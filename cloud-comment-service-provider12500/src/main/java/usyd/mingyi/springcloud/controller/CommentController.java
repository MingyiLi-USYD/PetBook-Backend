package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.service.CommentService;
import usyd.mingyi.springcloud.utils.BaseContext;

@RestController
@Slf4j
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/comment/{postId}")
    public R<Comment> addComment(@PathVariable("postId") Long postId, @RequestBody Comment comment) {

        comment.setPostId(postId);
        comment.setCommentTime(System.currentTimeMillis());
        comment.setUserId(BaseContext.getCurrentId());
        boolean success = commentService.save(comment);
        if(!success){
            throw new CustomException("添加失败");
        }
        return R.success(comment);
    }
    @GetMapping("/comments/received/count")
    public R<Long> countCommentsReceived(){
        Long userId = BaseContext.getCurrentId();

        Long count = commentService.count(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getTargetUserId, userId)
                .eq(Comment::getIsRead,false));
        return  R.success(count);
    }

    @GetMapping("/comments/{postId}")
    public R<Page<Comment>> getCommentsByPostId(@RequestParam("currPage") Long currPage, @RequestParam("pageSize") Integer pageSize, @PathVariable("postId") Long postId) {
        Page<Comment> commentsByPostId = commentService.getCommentsByPostId(currPage,pageSize,postId);
        return R.success(commentsByPostId);
    }


    @GetMapping("/comments")
    public R<Page<Comment>> getAllCommentsToMyPost(@RequestParam("current") Long current,
                                                      @RequestParam("pageSize") Integer pageSize){
        return R.success(commentService.getCommentsToMe(BaseContext.getCurrentId(),current,pageSize));
    }



    @GetMapping("/comment/read/{id}")
    public R<String> markCommentAsRead(@PathVariable("id") Long commentId){
        commentService.markAsRead(commentId,BaseContext.getCurrentId());
        return R.success("Success");
    }

    @PostMapping("/comment/reply")
    @ResponseBody
    public R<String> replyComment(@RequestBody Subcomment subcommentDto){
        Long id = BaseContext.getCurrentId();
        subcommentDto.setUserId(id);
        subcommentDto.setSubcommentTime(System.currentTimeMillis());
        commentService.saveSubcommentAndMarkAsRead(subcommentDto);
        return R.success("Success");
    }





}
