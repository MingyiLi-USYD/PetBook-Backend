package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.config.rest.FeignConfig;
import usyd.mingyi.springcloud.pojo.Post;

import java.util.List;

@FeignClient(value = "post-service-provider", configuration = FeignConfig.class)
public interface PostServiceFeign {
    @PostMapping("/post")
    String upLoadPost(@RequestBody Post post);

    @GetMapping("/post/{postId}")
    Post getPost(@PathVariable("postId") Long postId);

    @PutMapping("/post/{postId}")
    String changeVisibility(@PathVariable("postId") Long postId, @RequestParam("visibility") Boolean visibility);

    @DeleteMapping("/post/{postId}")
    String deletePost(@PathVariable("postId") Long postId);

    @GetMapping("/posts")
    IPage<Post> selectPage(@RequestParam("current") Long current,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam(value = "order", required = false) Integer order,
                           @RequestParam(value = "keywords", required = false) String keywords);

    @GetMapping("/posts/my")
    List<Post> getMyPosts();

    @GetMapping("/posts/{userId}")
    List<Post> getPostsByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/posts/ids")
    List<Post> getPostsByIds(@RequestBody List<Long> ids);

}
