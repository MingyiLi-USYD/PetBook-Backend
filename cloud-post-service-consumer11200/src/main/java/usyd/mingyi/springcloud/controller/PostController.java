package usyd.mingyi.springcloud.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.PostHandler;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.PostDto;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.PostServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.FieldUtils;

import java.util.List;

@RestController
@Slf4j
public class PostController {
    @Autowired
    PostServiceFeign postServiceFeign;
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    PoConvertToDto poConvertToDto;
    @GetMapping("/post/{postId}")
    public R<PostDto> getPost(@PathVariable Long postId) {
        Post post = postServiceFeign.getPost(postId);
        User userById = userServiceFeign.getUserById(post.getUserId());
        PostDto postDto = poConvertToDto.postToPostDto(post);
        postDto.setPostUser(userById);
        return R.success(postDto);
    }

    @DeleteMapping("/post/{postId}")
    public R<String> deletePost(@PathVariable("postId") Long postId) {
        postServiceFeign.deletePost(postId);
        return R.success("Successfully delete post");
    }

    @PutMapping("/post/{postId}")
    public R<String> changeVisibility(@PathVariable("postId") Long postId, @RequestParam("visibility") Boolean visibility) {
        return R.success( postServiceFeign.changeVisibility(postId,visibility));

    }

    @GetMapping("/posts")
    public R<Page<PostDto>> getPostsWithPagination(@RequestParam("current") Long current,
                                                    @RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam(value = "order",required = false) Integer order,
                                                    @RequestParam(value = "keywords",required = false)String keywords) {
        Page<Post> postPage = postServiceFeign.selectPage(current, pageSize, order,keywords);
        List<Post> records = postPage.getRecords();
        List<Long> userId = FieldUtils.extractField(records, Post::getUserId,true);
        List<User> userListByIds = userServiceFeign.getUserListByIds(userId);
        List<PostDto> postDtos = PostHandler.handleUserInfo(records, userListByIds);
        Page<PostDto> postDtoPage = new Page<>();
        BeanUtils.copyProperties(postPage,postDtoPage);
        postDtoPage.setRecords(postDtos);
        return R.success(postDtoPage);
    }


    @GetMapping("/my/posts")
    public R<List<PostDto>> getMyPosts() {
        List<Post> myPosts = postServiceFeign.getMyPosts();
        List<PostDto> postDtos = poConvertToDto.postToPostDto(myPosts);
        System.out.println(postDtos);
        postDtos.forEach(postDto -> postDto.setImages(postServiceFeign.getImagesByPostId(postDto.getPostId())));
        return R.success(postDtos);
    }



}
