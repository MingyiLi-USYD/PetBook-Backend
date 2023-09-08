package usyd.mingyi.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.config.rest.FeignConfig;
import usyd.mingyi.springcloud.pojo.User;

import java.util.List;

@Component
@FeignClient(value = "user-service-provider",configuration = FeignConfig.class)
public interface UserServiceFeign {
    @GetMapping("/user/{userId}") // 与UserController中的映射一致
    R<User> getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/user") // 与UserController中的映射一致
    String updateProfile(@RequestBody User user);


    @GetMapping("/users/byIds")
   List<User> getUserListByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("/currentUser") // 与UserController中的映射一致
    User getCurrentUser();

/*    @GetMapping("/user/{userId}")
    User getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/user")
    String updateProfile(@RequestBody User user);

    @GetMapping("/users/byIds")
    List<User> getUserListByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("/currentUser")
    User getCurrentUser();*/

}
