/*
package usyd.mingyi.springcloud.enhancer;

import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.pojo.User;
import java.util.List;

public interface UserServiceFeignEnhancer {
    @GetMapping("/user/{userId}")
    User getUserById(@PathVariable("userId") Long userId);

    @PutMapping("/user")
    String updateProfile(@RequestBody User user);

    @GetMapping("/users/byIds")
    List<User> getUserListByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("/currentUser")
    User getCurrentUser();
}
*/
