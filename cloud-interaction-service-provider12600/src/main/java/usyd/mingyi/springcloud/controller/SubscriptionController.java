package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.pojo.Subscription;
import usyd.mingyi.common.utils.BaseContext;

import usyd.mingyi.springcloud.service.SubscriptionService;


import java.util.List;

@RestController
@Slf4j
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;


    @GetMapping("/subscription/subscribes/ids")
    public R<List<String>> getAllSubscribesInIds() {
        Long userId = BaseContext.getCurrentId();
        return R.success(subscriptionService.getAllSubscribes(userId));

    }

    @GetMapping("/subscription/subscriber/ids")
    public R<List<String>> getAllSubscribersInIds() {
        Long userId = BaseContext.getCurrentId();
        return R.success(subscriptionService.getAllSubscribers(userId));
    }

    @GetMapping("/subscribe/{userId}")
    public R<String> subscribeUser(@PathVariable("userId") Long userId) {
        Long currentId = BaseContext.getCurrentId();
        if (currentId.equals(userId)) {
            return R.error("Can not subscribe yourself");
        }
        Subscription subscription = new Subscription();
        subscription.setMyId(currentId);
        subscription.setSubscribedUserId(userId);
        subscriptionService.save(subscription);
        return R.success("Subscribe success");
    }

    @DeleteMapping("/subscribe/{userId}")
    public R<String> unsubscribeUser(@PathVariable("userId") Long userId) {
        Long currentId = BaseContext.getCurrentId();
        if (currentId.equals(userId)) {
            return R.error("Can not unsubscribe yourself");
        }

        LambdaUpdateWrapper<Subscription> update = new LambdaUpdateWrapper<>();
        update.eq(Subscription::getMyId, currentId)
                .eq(Subscription::getSubscribedUserId, userId);
        if (!subscriptionService.remove(update)) {
            throw new CustomException("Never subscribe before");
        }

        return R.success("Unsubscribe success");
    }

}
