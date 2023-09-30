package usyd.mingyi.common.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.pojo.Comment;
import usyd.mingyi.common.pojo.Subcomment;

import java.util.List;

@FeignClient(value = "comment-service-provider", configuration = FeignConfig.class)
public interface CommentServiceFeign {
    @PostMapping("/comment")
    Comment addComment(@RequestBody Comment comment);

    @GetMapping("/comment/{commentId}")
    Comment getCommentByCommentId(@PathVariable("commentId") Long commentId);

    @GetMapping("/comments/received/count")
    Long countCommentsReceived();

    @GetMapping("/comments/{postId}")
    Page<Comment> getCommentsByPostId(@RequestParam("currPage") Long currPage, @RequestParam("pageSize") Integer pageSize, @PathVariable("postId") Long postId);

    @GetMapping("/comments")
    Page<Comment> getAllCommentsToMyPost(@RequestParam("current") Long current, @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/comment/read/{id}")
    String markCommentAsRead(@PathVariable("id") Long commentId);

    @PostMapping("/subcomment")
    Subcomment addSubcomment(@RequestBody Subcomment subcomment);

    @GetMapping("/subcomments/{commentId}")
    List<Subcomment> getSubcommentsByCommentId(@PathVariable("commentId") Long commentId);

    @GetMapping("/subcomments/limit/{commentId}")
    List<Subcomment> getSubcommentsByCommentIdLimit(@PathVariable("commentId") Long commentId);


    @GetMapping("/subcomment/count/{commentId}")
    Long countSubcommentSize(@PathVariable("commentId") Long commentId);
}
