package usyd.mingyi.springcloud.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.dto.PostDto;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.service.PostServiceFeign;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class PostController {
    @Autowired
    PostServiceFeign postServiceFeign;
    @GetMapping("/post/{postId}")
    public R<Post> getPost(@PathVariable Long postId) {
        Post post = postServiceFeign.getPost(postId);
        if (!post.getVisible() && !post.getUserId().equals(BaseContext.getCurrentId())) {
            throw new CustomException("This post is currently invisible");
        }
        return R.success(post);
    }

    @DeleteMapping("/post/{postId}")
    public R<String> deletePost(@PathVariable("postId") Long postId) {
        postServiceFeign.deletePost(postId);
        return R.success("Successfully delete post");
    }

    @GetMapping("/posts")
    public R<Page<PostDto>> getPostsWithPagination(@RequestParam("current") Long current,
                                                    @RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam(value = "order",required = false) Integer order,
                                                    @RequestParam(value = "keywords",required = false)String keywords) {
        Page<Post> postPage = postServiceFeign.selectPage(current, pageSize, order,keywords);
        List<Post> records = postPage.getRecords();
        if (!records.isEmpty()) {

        }
        Page<PostDto> postDtoPage = new Page<>();
        BeanUtils.copyProperties(postPage,postDtoPage);
      //  List<PostDto> records = postDtoPage.getRecords();




        return null;
       // return R.success(allPosts);
    }


}
