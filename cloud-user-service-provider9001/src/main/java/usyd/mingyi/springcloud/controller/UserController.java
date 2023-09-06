package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.mapper.UserMapper;
import usyd.mingyi.springcloud.pojo.User;

@RestController
@Slf4j
@RefreshScope
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/{userId}")
    public R<User> getCurrentUser(@PathVariable("userId") Long userId) {
        User user = userMapper.selectById(userId);
        return R.success(user);
    }

    @GetMapping("/user")
    public R<User> getCurrentUserByUsername(@RequestParam("username") String username) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        return R.success(user);
    }
}
