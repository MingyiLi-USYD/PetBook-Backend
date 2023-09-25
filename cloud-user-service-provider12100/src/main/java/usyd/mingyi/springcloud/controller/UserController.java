package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserService;
import usyd.mingyi.springcloud.utils.BaseContext;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@RestController
@Slf4j
@RefreshScope
@Validated
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user/{userId}")
    public R<User> getUserById(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            throw new CustomException("No user found");
        }
        return R.success(user);
    }

    @GetMapping("/users")
    public R<Page<User>> getAllUser(@RequestParam("current") Long current
            , @RequestParam("size") Long size, @RequestParam("keywords") String keywords) {
        return R.success(userService.getPageUserList(current, size, keywords));
    }

    @GetMapping("/users/byIds")
    public R<List<User>> getUserListByIds(@RequestParam("ids") @NotNull Collection<Long> ids) {
        if (ids.isEmpty()) {
            return R.success(new ArrayList<>());
        }
        List<User> users = userService.listByIds(ids);
        return R.success(users);
    }

    @PutMapping("/user")
    public R<String> updateProfile(@RequestBody User user) {
        user.setUserId(BaseContext.getCurrentId());
        user.setRole(null);
        userService.updateById(user);
        return R.success("Success");
    }

    @GetMapping("/currentUser")
    public R<User> getCurrentUser() {
        log.info(BaseContext.getCurrentId().toString());
        Long currentId = BaseContext.getCurrentId();
        User user = userService.getById(currentId);
        if (user == null) {
            throw new CustomException("Login first");
        }
        return R.success(user);
    }

    @GetMapping("/user/thirdPart")
    public R<User> register(@PathVariable("userId") Long userId) {
        User user = userService.getById(userId);
        if (user == null) {
            throw new CustomException("No user found");
        }
        return R.success(user);
    }


}
