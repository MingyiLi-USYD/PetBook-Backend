package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.LovePost;
import usyd.mingyi.springcloud.service.LovePostService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class LovePostController {
    @Autowired
    LovePostService lovePostService;

    @GetMapping("/lovePosts")
    public R<Page<LovePost>> LovePostsToMe(@RequestParam("current") Long current,
                                           @RequestParam("pageSize") Integer pageSize) {
        return R.success(lovePostService.getLovePostsToMe(BaseContext.getCurrentId(), current, pageSize));
    }
    @GetMapping("/lovePosts/received/count")
    public R<Long> count() {
        return R.success(lovePostService.countLovePostsReceived(BaseContext.getCurrentId()));
    }
    @GetMapping("/lovePosts/my/ids")
    public R<List<Long>> getAllLovedPostsId() {

        Long currentId = BaseContext.getCurrentId();
        log.info(currentId.toString());
        return R.success(lovePostService.getLovePosts(currentId)
                .stream().map(LovePost::getPostId).collect(Collectors.toList()));
    }

    @GetMapping("/lovePost/read/{id}")
    public R<String> markAsRead(@PathVariable("id")Long lovePostId) {
        lovePostService.markLovePostRead(BaseContext.getCurrentId(),lovePostId);
        return R.success("Success");
    }

    @GetMapping("/love/{postId}")
    @Transactional //分支事务
    public R<String> love(@PathVariable("postId") Long postId,@RequestParam("postUserId") Long postUserId) {
        Long userId = BaseContext.getCurrentId();
        lovePostService.lovePost(userId,postId,postUserId);
        return R.success("success");
    }

    @DeleteMapping("/love/{postId}")
    public R<String> cancelLove(@PathVariable("postId") Long postId,@RequestParam("postUserId") Long postUserId) {
        Long userId = BaseContext.getCurrentId();
        lovePostService.cancelLovePost(userId,postId,postUserId);

        return R.success("success");
    }
}
