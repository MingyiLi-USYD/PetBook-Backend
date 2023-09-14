package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.service.InteractionServiceFeign;

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

}
