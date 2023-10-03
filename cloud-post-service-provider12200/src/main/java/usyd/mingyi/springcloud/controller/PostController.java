package usyd.mingyi.springcloud.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class PostController {
    @Autowired
    PostService postService;


    @PostMapping("/post")
    public R<Post> upLoadPost(@RequestBody @Valid Post post) {
        post.setPostTime(System.currentTimeMillis());
        post.setUserId(BaseContext.getCurrentId());

        if (!post.getIsDelay()) {
            //立刻上传Post
            post.setPublishTime(System.currentTimeMillis());
            postService.save(post);
        } else {
            // 获取日期时间戳
            long targetTime = 1;
            //放入MQ死信队列 设置TTL
            long currentTimeMillis = System.currentTimeMillis();
            long TTL = targetTime - currentTimeMillis;
            post.setPublishTime(targetTime);
            //放入消息队列
        }
        log.info("上传文件");
        return R.success(post);
    }

    @GetMapping("/post/{postId}")
    @DS("slave")
    public R<Post> getPost(@PathVariable("postId") Long postId) {

        Post post = postService.getById(postId);
        if (!post.getVisible() && !post.getUserId().equals(BaseContext.getCurrentId())) {
            throw new CustomException("This post is currently invisible");
        }
        return R.success(post);
    }

    @PutMapping("/post/{postId}")
    public R<String> changeVisibility(@PathVariable("postId") Long postId, @RequestParam("visibility") Boolean visibility) {

        Post post = postService.getById(postId);
        if (!post.getUserId().equals(BaseContext.getCurrentId())) {
            return R.error("No right to access");
        } else {
            post.setVisible(visibility);
            if (postService.updateById(post)) {
                return R.success("Update success");
            } else {
                return R.error("No change happen");
            }
        }
    }

    @DeleteMapping("/post/{postId}")
    public R<String> deletePost(@PathVariable("postId") Long postId) {
        Post post = postService.getById(postId);
        if (post == null || !post.getUserId().equals(BaseContext.getCurrentId())) {
            throw new CustomException("没有找到");
        } else {
            postService.removeById(postId);
        }

        return R.success("Successfully delete post");
    }


    @GetMapping("/posts")
    public R<Page<Post>> selectPage(@RequestParam("current") Long current, @RequestParam("pageSize") Integer pageSize, @RequestParam(value = "order", required = false) Integer order, @RequestParam(value = "keywords", required = false) String keywords) {
        Page<Post> allPosts = postService.getAllPosts(current, pageSize, order, keywords);
        return R.success(allPosts);
    }

    @GetMapping("/posts/my")
    public R<List<Post>> getMyPosts() {
        Long userId = BaseContext.getCurrentId();
        List<Post> myPosts = postService.getPostsByUserId(userId);
        return R.success(myPosts);
    }

    @GetMapping("/posts/{userId}")
    public R<List<Post>> getPostsByUserId(@PathVariable("userId") Long userId) {
        List<Post> posts = postService.getPostsByUserIdVisible(userId);
        return R.success(posts);
    }


    @GetMapping("/posts/ids")
    public R<List<Post>> getPostsByIds(@RequestBody List<Long> ids) {

        List<Post> postsByIds = postService.getPostsByIds(ids);
        return R.success(postsByIds);
    }

    @GetMapping("/post/changeLove")
    @DS("master")
    @Transactional
    public R<Post> changeLoveOfPostOptimistic(@RequestParam("postId") Long postId, @RequestParam("delta") Integer delta) {
        return R.success(postService.changeLoveNumber(postId, delta));
    }


}
