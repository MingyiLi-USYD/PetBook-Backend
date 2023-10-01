package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.feign.InteractionServiceFeign;

import java.util.List;

@RestController
@Slf4j
public class SubscriptionController {
    @Autowired
    InteractionServiceFeign interactionServiceFeign;

    @GetMapping("/subscription/subscribes/ids")
    public R<List<String>> getAllSubscribesInIds() {

        return R.success(interactionServiceFeign.getAllSubscribesInIds());

    }

    @GetMapping("/subscription/subscriber/ids")
    public R<List<String>> getAllSubscribersInIds() {

        return R.success(interactionServiceFeign.getAllSubscribersInIds());
    }

    @GetMapping("/subscription/subscribe/{userId}")
    public R<String> subscribe(@PathVariable("userId")Long userId) {

        return R.success(  interactionServiceFeign.subscribeUser(userId));
    }

    @DeleteMapping("/subscription/subscribe/{userId}")
    public R<String> unsubscribe(@PathVariable("userId")Long userId) {
        return R.success(  interactionServiceFeign.unsubscribeUser(userId));
    }

}
