package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.service.PostService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/post")
    public R<String> upLoadPost(@RequestBody @Validated Post post) {/*
        postService.save(post);
        post.setPostTime(System.currentTimeMillis());
        post.setUserId(BaseContext.getCurrentId());
        if (post == null) {
            //立刻上传Post
            post.setPublishTime(System.currentTimeMillis());
        } else {
            // 获取日期时间戳
            long targetTime = post.getEstimateDate().getTime();
            //放入MQ死信队列 设置TTL
            long currentTimeMillis = System.currentTimeMillis();
            long TTL = targetTime - currentTimeMillis;
            post.setPublishTime(targetTime);
            //放入消息队列
        }
        log.info("上传文件");*/
        return R.success("Successfully upload");
    }

    @GetMapping("/post/{postId}")
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
       if(post==null||!post.getUserId().equals(BaseContext.getCurrentId())){
           throw new CustomException("没有找到");
       }
        return R.success("Successfully delete post");
    }



    @GetMapping("/posts")
    public R<IPage<Post>> selectPage(@RequestParam("current") Long current,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam(value = "order",required = false) Integer order,
                                     @RequestParam(value = "keywords",required = false)String keywords){
        IPage<Post> allPosts = postService.getAllPosts(current, pageSize, order,keywords);
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

/*
    @GetMapping("/posts/ids/{userId}")
    public R<List<Long>> getPostIds(@PathVariable("userId") Long userId) {
        List<Post> posts = postService.getPostsByUserIdVisible(userId);
        return R.success(posts.stream().map());
    }
*/

    @GetMapping("/posts/ids")
    public R<List<Post>> getPostsByIds(@RequestBody List<Long> ids) {
        List<Post> postsByIds = postService.getPostsByIds(ids);
        return R.success(postsByIds);
    }





}
