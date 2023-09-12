package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CommentHandler;
import usyd.mingyi.springcloud.common.CommentMapper;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.CommentDto;
import usyd.mingyi.springcloud.dto.SubcommentDto;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.CommentServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.FieldUtils;

import java.util.List;

@RestController
@Slf4j
public class CommentController {
    @Autowired
    CommentServiceFeign commentServiceFeign;
    @Autowired
    PoConvertToDto poConvertToDto;
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    CommentMapper commentMapper;

    @PostMapping("/comment/{postId}")
    public R<CommentDto> addComment(@PathVariable("postId") Long postId, @RequestBody Comment comment) {
        Comment commentRes = commentServiceFeign.addComment(postId, comment);
        CommentDto commentDto = poConvertToDto.commentToCommentDto(commentRes);
        commentDto.setCommentUser(userServiceFeign.getCurrentUser());
        return R.success(commentDto);
    }

    @GetMapping("/comments/{postId}")
    public R<Page<CommentDto>> getCommentsByPostId(@RequestParam("currPage") Long currPage,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @PathVariable("postId") Long postId) {
        Page<Comment> commentPage = commentServiceFeign.getCommentsByPostId(currPage, pageSize, postId);
        return getCommentDtoPage(commentPage);
    }

    @GetMapping("/comments")
    public R<Page<CommentDto>> getAllCommentsToMyPost(@RequestParam("current") Long current,
                                                      @RequestParam("pageSize") Integer pageSize){

        Page<Comment> commentPage = commentServiceFeign.getAllCommentsToMyPost(current, pageSize);
        return getCommentDtoPage(commentPage);
    }

    @GetMapping("/comment/read/{id}")
    public R<String> markCommentAsRead(@PathVariable("id") Long commentId){
        String res = commentServiceFeign.markCommentAsRead(commentId);
        return R.success(res);
    }

    @PostMapping("/comment/reply")
    @ResponseBody
    public R<String> replyComment(@RequestBody SubcommentDto subcommentDto){

        return R.success("Success");
    }


    @NotNull
    private R<Page<CommentDto>> getCommentDtoPage(Page<Comment> commentPage) {
        List<Comment> records = commentPage.getRecords();
        List<Long> userIds = FieldUtils.extractField(records, Comment::getUserId);
        List<User> userListByIds = userServiceFeign.getUserListByIds(userIds);
        List<CommentDto> commentDtos = CommentHandler.handleUserInfo(records, userListByIds);
        Page<CommentDto> commentDtoPage = commentMapper.convertPage(commentPage);
        commentDtoPage.setRecords(commentDtos);
        return R.success(commentDtoPage);
    }



}
