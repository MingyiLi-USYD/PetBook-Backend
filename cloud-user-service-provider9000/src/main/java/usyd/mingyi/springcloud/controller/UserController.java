package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.mapper.UserMapper;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
@RefreshScope
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/user/{userId}")
    public R<User> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        if (user==null){
            throw new CustomException("No user found");
        }
        return R.success(user);
    }

    @GetMapping("/users/byIds")
    public R<List<User>> getUserListByIds(@RequestParam("ids") List<Long> ids ) {
        List<User> users = userService.listByIds(ids);
        return R.success(users);
    }
/*    @GetMapping("/user")
    public R<User> getCurrentUserByUsername(@RequestParam("username") String username) {
        User user = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        return R.success(user);
    }*/
    @PutMapping("/user")
    public R<String> updateProfile(@RequestBody User user) {
        user.setUserId(BaseContext.getCurrentId());
        user.setRole(null);
        userService.updateById(user);
        return R.success("Success");
    }

    @GetMapping("/currentUser")
    public R<User> getCurrentUser() {
        Long currentId = BaseContext.getCurrentId();
        User user = userService.getById(currentId);
        if (user == null) {
            throw new CustomException("Login first");
        }
        return R.success(user);
    }

}
