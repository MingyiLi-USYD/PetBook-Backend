package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Comment;
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

    @GetMapping("/comments/{postId}")
    public R<IPage<Comment>> getCommentsByPostId(@RequestParam("currPage") Long currPage, @RequestParam("pageSize") Integer pageSize, @PathVariable("postId") Long postId) {
        IPage<Comment> commentsByPostId = commentService.getCommentsByPostId(currPage,pageSize,postId);
        return R.success(commentsByPostId);
    }


    @GetMapping("/comments/tome")
    @ResponseBody
    public R<IPage<Comment>> getAllCommentsToMyPost(@RequestParam("current") Long current,
                                                      @RequestParam("pageSize") Integer pageSize){

        return R.success(commentService.getCommentsToMe(BaseContext.getCurrentId(),current,pageSize));
    }


}
