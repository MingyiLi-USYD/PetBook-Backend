package usyd.mingyi.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.User;

@Component
@FeignClient(value = "user-service-provider")
public interface UserServiceFeign {
    @GetMapping("/user/{userId}")
    R<User> getCurrentUser(@PathVariable("userId") Long userId);

}
