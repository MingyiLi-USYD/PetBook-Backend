package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.service.SubscriptionService;
import usyd.mingyi.springcloud.utils.BaseContext;

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

}
