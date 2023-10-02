package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.dto.LovePostDto;
import usyd.mingyi.common.entity.ServiceMessage;
import usyd.mingyi.common.entity.ServiceMessageType;
import usyd.mingyi.common.feign.ChatServiceFeign;
import usyd.mingyi.common.feign.InteractionServiceFeign;
import usyd.mingyi.common.feign.PostServiceFeign;
import usyd.mingyi.common.feign.UserServiceFeign;
import usyd.mingyi.common.pojo.LovePost;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.mapstruct.PoConvertToDto;

@RestController
@Slf4j
public class LovePostController {
    @Autowired
    InteractionServiceFeign interactionServiceFeign;
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    PostServiceFeign postServiceFeign;
    @Autowired
    ChatServiceFeign chatServiceFeign;

    @Autowired
    PoConvertToDto poConvertToDto;

    @GetMapping("/lovePosts")
    public R<Page<LovePostDto>> LovePostsToMe(@RequestParam("current") Long current,
                                              @RequestParam("pageSize") Integer pageSize) {
        Page<LovePost> lovePostPage = interactionServiceFeign.LovePostsToMe(current, pageSize);
        Page<LovePostDto> lovePostDtoPage = poConvertToDto.convertLovePostPage(lovePostPage);
        lovePostDtoPage.getRecords().forEach(lovePostDto -> {
            lovePostDto.setUserInfo(userServiceFeign.getUserById(lovePostDto.getUserId()));
            lovePostDto.setRelevantPost(postServiceFeign.getPostByPostId(lovePostDto.getPostId()));
        });
        return R.success(lovePostDtoPage);
    }

    @GetMapping("/lovePost/read/{id}")
    public R<String> markAsRead(@PathVariable("id") Long lovePostId) {
        return R.success(interactionServiceFeign.markAsRead(lovePostId));
    }

    //需要分布式事务
    @GetMapping("/lovePost/{postId}")
    @GlobalTransactional
    public R<String> love(@PathVariable("postId") Long postId) {

        Post post = postServiceFeign.getPostByPostId(postId);
        postServiceFeign.changeLoveOfPostOptimistic(postId, 1);
        String res = interactionServiceFeign.love(postId, post.getUserId());

        //socket推送消息
        Long currentId = BaseContext.getCurrentId();
        ServiceMessage serviceMessage = new ServiceMessage(currentId, System.currentTimeMillis(),
                post.getUserId(), ServiceMessageType.NEW_LIKE);
        chatServiceFeign.sendServiceMessage(serviceMessage);
        return R.success(res);
    }

    @DeleteMapping("/lovePost/{postId}")
    @GlobalTransactional
    public R<String> cancelLove(@PathVariable("postId") Long postId) {
        Post post = postServiceFeign.getPostByPostId(postId);
        postServiceFeign.changeLoveOfPostOptimistic(postId, -1);
        return R.success(interactionServiceFeign.cancelLove(postId, post.getUserId()));
    }
}
