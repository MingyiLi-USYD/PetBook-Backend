package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.pojo.UserInfo;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.mongodb.service.UserInfoService;
import usyd.mingyi.springcloud.service.UserService;

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
    @Autowired
    UserInfoService userInfoService;


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
        if (user == null) {
            throw new CustomException("Login first");
        }
        return R.success(user);
    }

    @GetMapping("/comment/love/{commentId}")
    public R<String> addLovedCommentId(@PathVariable("commentId") Long commentId) {

        if (userInfoService.addCommentToLovedComments(BaseContext.getCurrentId(), commentId)) {

            return R.error("插入失败");
        }

        return R.success("插入成功");

    }

    @DeleteMapping("/comment/love/{commentId}")
    public R<String> removeLovedCommentId(@PathVariable("commentId") Long commentId) {

        if (userInfoService.removeCommentFromLovedComments(BaseContext.getCurrentId(), commentId)) {

            return R.error("移除失败");
        }

        return R.success("移除成功");
    }


    @GetMapping("/subcomment/love/{subcommentId}")
    public R<String> addLovedSubcommentId(@PathVariable("subcommentId") Long subcommentId) {

        if (userInfoService.addSubCommentToLovedSubcomments(BaseContext.getCurrentId(), subcommentId)) {

            return R.error("插入失败");
        }

        return R.success("插入成功");

    }

    @DeleteMapping("/subcomment/love/{subcommentId}")
    public R<String> removeLovedSubcommentId(@PathVariable("subcommentId") Long subcommentId) {

        if (userInfoService.removeSubcommentFromLovedSubcomments(BaseContext.getCurrentId(), subcommentId)) {

            return R.error("移除失败");
        }

        return R.success("移除成功");
    }

    @GetMapping("/user/userInfo")
    public R<UserInfo> getUserInfo() {
          return R.success(userInfoService.getUserInfoByUserId(BaseContext.getCurrentId()));
    }


}
