package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.config.rest.FeignConfig;
import usyd.mingyi.springcloud.pojo.Comment;

@FeignClient(value = "comment-service-provider",configuration = FeignConfig.class)
public interface CommentServiceFeign {
    @PostMapping("/comment/{postId}")
    Comment addComment(@PathVariable("postId") Long postId, @RequestBody Comment comment);

    @GetMapping("/comments/received/count")
    Long countCommentsReceived();

    @GetMapping("/comments/{postId}")
    IPage<Comment> getCommentsByPostId(@RequestParam("currPage") Long currPage, @RequestParam("pageSize") Integer pageSize, @PathVariable("postId") Long postId);

    @GetMapping("/comments")
    IPage<Comment> getAllCommentsToMyPost(@RequestParam("current") Long current, @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/comment/read/{id}")
    String markCommentAsRead(@PathVariable("id") Long commentId);
}
