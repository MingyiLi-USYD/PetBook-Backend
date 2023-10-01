package usyd.mingyi.common.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.pojo.LovePost;
import usyd.mingyi.common.pojo.Mention;


import java.util.List;

@FeignClient(value = "interaction-service-provider",configuration = FeignConfig.class)
public interface InteractionServiceFeign {

    //下面是跟点赞相关的操作
    @GetMapping("/lovePosts")
    Page<LovePost> LovePostsToMe(@RequestParam("current") Long current,
                                 @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/lovePosts/received/count")
    Long countLovePostsReceived();

    @GetMapping("/lovePosts/my/ids")
    List<Long> getAllLovedPostsId();

    @GetMapping("/lovePost/read/{id}")
    String markAsRead(@PathVariable("id") Long lovePostId);

    @GetMapping("/love/{postId}")
    String love(@PathVariable("postId") Long postId, @RequestParam("postUserId") Long postUserId);

    @DeleteMapping("/love/{postId}")
    String cancelLove(@PathVariable("postId") Long postId, @RequestParam("postUserId") Long postUserId);

    //下面是跟被post mention的操作

    @PostMapping("/mention")
    String addMention(@RequestBody Mention mention);
    @GetMapping("/mentions")
    Page<Mention> getAllMentionedPosts(@RequestParam("current") Long current,
                                       @RequestParam("pageSize") Integer pageSize);

    @GetMapping("/mention/read/{mentionId}")
    String markMentionAsRead(@PathVariable("mentionId") Long mentionId);

    @GetMapping("/mentions/received/count")
    Long countMentionsReceived();

    //下面是跟订阅相关的
    @GetMapping("/subscription/subscribes/ids")
    List<String> getAllSubscribesInIds();

    @GetMapping("/subscription/subscriber/ids")
    List<String> getAllSubscribersInIds();

    @GetMapping("/subscribe/{userId}")
    String subscribeUser(@PathVariable("userId") Long userId);

    @DeleteMapping("/subscribe/{userId}")
    String unsubscribeUser(@PathVariable("userId") Long userId);


}
