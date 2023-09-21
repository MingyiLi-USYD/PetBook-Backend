package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CommentHandler;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.CommentDto;
import usyd.mingyi.springcloud.dto.SubcommentDto;
import usyd.mingyi.springcloud.entity.ServiceMessage;
import usyd.mingyi.springcloud.entity.ServiceMessageType;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.*;
import usyd.mingyi.springcloud.utils.BaseContext;
import usyd.mingyi.springcloud.utils.FieldUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class CommentController {
    @Autowired
    CommentServiceFeign commentServiceFeign;

    @Autowired
    PostServiceFeign postServiceFeign;

    @Autowired
    InteractionServiceFeign interactionServiceFeign;

    @Autowired
    ChatServiceFeign chatServiceFeign;

    @Autowired
    PoConvertToDto poConvertToDto;

    @Autowired
    UserServiceFeign userServiceFeign;
;

    @PostMapping("/comment")
    public R<CommentDto> addComment(@RequestBody @Valid Comment comment) {
        Post post = postServiceFeign.getPostByPostId(comment.getPostId());
        if(post==null){
            throw new CustomException("Post 不存在");
        }
        Comment commentRes = commentServiceFeign.addComment(comment);
        CommentDto commentDto = poConvertToDto.commentToCommentDto(commentRes);
        commentDto.setCommentUser(userServiceFeign.getCurrentUser());

        //socket推送消息
        Long currentId = BaseContext.getCurrentId();
        ServiceMessage serviceMessage = new ServiceMessage(currentId,System.currentTimeMillis(),
                post.getUserId(), ServiceMessageType.NEW_COMMENT);
        chatServiceFeign.sendServiceMessage(serviceMessage);

        return R.success(commentDto);
    }

    @GetMapping("/comments/{postId}")
    public R<Page<CommentDto>> getCommentsByPostId(@RequestParam("current") Long currPage,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @PathVariable("postId") Long postId) {
        Page<Comment> commentPage = commentServiceFeign.getCommentsByPostId(currPage, pageSize, postId);
        return getCommentDtoPage(commentPage);
    }

    @GetMapping("/comments")
    public R<Page<CommentDto>> getAllCommentsToMyPost(@RequestParam("current") Long current,
                                                      @RequestParam("pageSize") Integer pageSize) {

        Page<Comment> commentPage = commentServiceFeign.getAllCommentsToMyPost(current, pageSize);
        return getCommentDtoPage(commentPage);
    }

    @GetMapping("/comment/read/{id}")
    public R<String> markCommentAsRead(@PathVariable("id") Long commentId) {
        String res = commentServiceFeign.markCommentAsRead(commentId);
        return R.success(res);
    }


    @PostMapping("/comment/reply")
    public R<String> replyComment(@RequestBody Subcomment subcomment) {

        return R.success("Success");
    }

    @NotNull
    private R<Page<CommentDto>> getCommentDtoPage(Page<Comment> commentPage) {
        //发起了很多次查询 后期考虑后期加入缓存机制  或者一次性查出多条数据再合并

        Page<CommentDto> commentDtoPage = poConvertToDto.convertPage(commentPage);
        commentDtoPage.getRecords().forEach(commentDto -> {
            commentDto.setCommentUser(userServiceFeign.getUserById(commentDto.getUserId()));
            List<Subcomment> subcomments =
                    commentServiceFeign.getSubcommentsByCommentIdLimit(commentDto.getCommentId());
            List<SubcommentDto> subcommentDtos = poConvertToDto.subcommentsToSubcommentDtos(subcomments);
            subcommentDtos.forEach(subcommentDto ->{
                subcommentDto.setSubcommentUser(userServiceFeign.getUserById(subcommentDto.getUserId()));
                        if (subcommentDto.getIsReply()) {
                            subcommentDto.setRelpyUser(userServiceFeign.getUserById(subcommentDto.getReplyUserId()));
                        }
            }

            );
            commentDto.setSubcommentDtos(subcommentDtos);
            commentDto.setSubcommentsLength(commentServiceFeign.countSubcommentSize(commentDto.getCommentId()));
        });

        return R.success(commentDtoPage);
    }


}
