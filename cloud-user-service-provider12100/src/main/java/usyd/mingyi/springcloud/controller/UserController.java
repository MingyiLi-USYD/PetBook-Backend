package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.common.UnauthorizedException;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.service.UserService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @PutMapping("/user/changeUser")
    public R<String> changeUserRoleAndStatus(@RequestBody User user) {
        userService.updateById(user);
        return R.success("Success");
    }

    @GetMapping("/currentUser")
    public R<User> getCurrentUser() {

        Long currentId = BaseContext.getCurrentId();
        User user = userService.getById(currentId);

        return R.success(user);
    }

    @PutMapping("/user/profile")
    public R<String> updateUserProfile(@RequestParam("nickname") String nickname,
                                     @RequestParam("description") String description) {
        if (userService.update(
                new LambdaUpdateWrapper<User>()
                        .set(User::getNickname,nickname)
                        .set(User::getDescription,description)
                .eq(User::getUserId, BaseContext.getCurrentId())
        )) {
            return R.success("Success to update");
        }

          throw new CustomException("Fail to update");
    }



}
