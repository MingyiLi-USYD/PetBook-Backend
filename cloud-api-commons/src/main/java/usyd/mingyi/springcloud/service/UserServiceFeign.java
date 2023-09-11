package usyd.mingyi.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.config.FeignConfig;
import usyd.mingyi.springcloud.pojo.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@FeignClient(value = "user-service-provider",configuration = FeignConfig.class)
public interface UserServiceFeign {
    @GetMapping("/user/{userId}") // 与UserController中的映射一致
    User getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/user") // 与UserController中的映射一致
    String updateProfile(@RequestBody User user);


    @GetMapping("/users/byIds")
    List<User> getUserListByIds(@RequestParam("ids") Collection<Long> ids);

    @GetMapping("/currentUser") // 与UserController中的映射一致
    User getCurrentUser();

}
