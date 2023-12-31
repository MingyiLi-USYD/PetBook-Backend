package usyd.mingyi.common.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.pojo.PostImage;
import java.util.List;

@FeignClient(value = "post-service-provider", configuration = FeignConfig.class)
public interface PostServiceFeign {
    @PostMapping("/post")
    Post upLoadPost(@RequestBody Post post);

    @GetMapping("/post/{postId}")
    Post getPostByPostId(@PathVariable("postId") Long postId);

    @PutMapping("/post/{postId}")
    String changeVisibility(@PathVariable("postId") Long postId, @RequestParam("visibility") Boolean visibility);

    @DeleteMapping("/post/{postId}")
    String deletePost(@PathVariable("postId") Long postId);

    @GetMapping("/posts")
    Page<Post> selectPage(@RequestParam("current") Long current,
                          @RequestParam("pageSize") Integer pageSize,
                          @RequestParam(value = "order", required = false) Integer order,
                          @RequestParam(value = "keywords", required = false) String keywords);

    @GetMapping("/posts/my")
    List<Post> getMyPosts();

    @GetMapping("/posts/{userId}")
    List<Post> getPostsByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/posts/ids")
    List<Post> getPostsByIds(@RequestBody List<Long> ids);

    @PostMapping("/posts/page/ids")
    Page<Post> getPostsByIdsWithPage(@RequestBody List<Long> ids, @RequestParam("current") Long current,
                                               @RequestParam("pageSize") Integer pageSize);


    @GetMapping("/post/changeLove")
    Post changeLoveOfPostOptimistic(@RequestParam("postId") Long postId, @RequestParam("delta") Integer delta);

    //下面是图片相关的
    @GetMapping("/images/post/{postId}")
    List<PostImage> getImagesByPostId(@PathVariable("postId") Long postId);

    @DeleteMapping("/images/post/{postId}")
    String deleteImagesByPostId(@PathVariable("postId") Long postId);

    @PostMapping("/images/post")
    String savePostImages(@RequestBody List<PostImage> postImages);

}
