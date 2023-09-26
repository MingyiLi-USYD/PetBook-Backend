package usyd.mingyi.springcloud.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.PostHandler;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.PostDto;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.PostImage;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.*;
import usyd.mingyi.springcloud.utils.FieldUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class PostController {
    @Autowired
    PostServiceFeign postServiceFeign;
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    FriendServiceFeign friendServiceFeign;
    @Autowired
    InteractionServiceFeign interactionServiceFeign;
    @Autowired
    ObjectStorageServiceFeign objectStorageServiceFeign;

    @Autowired
    PoConvertToDto poConvertToDto;

    @PostMapping("/post")  //需要分布式事务
    @GlobalTransactional
    public R<String> addPost(@Valid Post post,
                             @RequestParam("images") MultipartFile[] images,
                             @RequestParam("mentions") List<Long> userIds) {

        if (post.getVisible()) {
            for (Long userId : userIds) {
                int friendshipStatus = friendServiceFeign.getFriendshipStatus(userId); //检查之间是否是好友
                if (friendshipStatus != 1) {
                    //1代表是好友 只要不上1 就不是好友 拒绝此次操作
                    throw new CustomException("提及的人存在非好友");
                }
            }
            if (!post.getIsDelay()) {// 表示不需要定时上传   所以说立刻发布
                List<String> imageUrlList = objectStorageServiceFeign.savePostImages(images);
                post.setCoverImage(imageUrlList.get(0));
                Post saved = postServiceFeign.upLoadPost(post);

                List<PostImage> postImages = new ArrayList<>();
                for (String url : imageUrlList) {
                    PostImage postImage = new PostImage();
                    postImage.setImageUrl(url);
                    postImage.setPostId(saved.getPostId());
                    postImages.add(postImage);
                }
                postServiceFeign.savePostImages(postImages);

            } else {//需要延迟上传 通过MQ死信队列完成

            }
        }else {
            //如果是私人的 就不需要通知好友 直接上传就行了


        }




        return R.success("上传成功");
    }

    @GetMapping("/post/{postId}")
    public R<PostDto> getPost(@PathVariable Long postId) {
        //可以使用promise 并发查询 但是这里并发量不大 串行查询就行了
        Post post = postServiceFeign.getPostByPostId(postId);
        List<PostImage> images = postServiceFeign.getImagesByPostId(postId);

        User userById = userServiceFeign.getUserById(post.getUserId());
        PostDto postDto = poConvertToDto.postToPostDto(post);

        postDto.setImages(images);
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
        return R.success(postServiceFeign.changeVisibility(postId, visibility));

    }

    @GetMapping("/posts")
    public R<Page<PostDto>> getPostsWithPagination(@RequestParam("current") Long current,
                                                   @RequestParam("pageSize") Integer pageSize,
                                                   @RequestParam(value = "order", required = false) Integer order,
                                                   @RequestParam(value = "keywords", required = false) String keywords) {
        Page<Post> postPage = postServiceFeign.selectPage(current, pageSize, order, keywords);
        List<Post> records = postPage.getRecords();
        List<Long> userId = FieldUtils.extractField(records, Post::getUserId, true);
        List<User> userListByIds = userServiceFeign.getUserListByIds(userId);
        List<PostDto> postDtos = PostHandler.handleUserInfo(records, userListByIds);
        Page<PostDto> postDtoPage = new Page<>();
        BeanUtils.copyProperties(postPage, postDtoPage);
        postDtoPage.setRecords(postDtos);
        return R.success(postDtoPage);
    }


    @GetMapping("/posts/my")
    public R<List<PostDto>> getMyPosts() {
        List<Post> myPosts = postServiceFeign.getMyPosts();
        List<PostDto> postDtos = poConvertToDto.postToPostDto(myPosts);
        postDtos.forEach(postDto -> postDto.setImages(postServiceFeign.getImagesByPostId(postDto.getPostId())));
        return R.success(postDtos);
    }

}
