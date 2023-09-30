package usyd.mingyi.common.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.pojo.User;


import java.util.Collection;
import java.util.List;


@FeignClient(value = "user-service-provider",configuration = FeignConfig.class)
public interface UserServiceFeign {
    @GetMapping("/user/{userId}") // 与UserController中的映射一致
    User getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/user") // 与UserController中的映射一致
    String updateProfile(@RequestBody User user);


    @GetMapping("/users/byIds")
    List<User> getUserListByIds(@RequestParam("ids") Collection<Long> ids);

    @GetMapping("/users")
    Page<User> getAllUser(@RequestParam("current") Long current
            , @RequestParam("size") Long size, @RequestParam("keywords") String keywords) ;

    @GetMapping("/currentUser") // 与UserController中的映射一致
    User getCurrentUser();

}
