package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserServiceFeign;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserServiceFeign userServiceFeign;
    @GetMapping("/consumer/user/{userId}")
    public R<User> getUser(@PathVariable("userId") Long userId){
      return   userServiceFeign.getCurrentUser(userId);
    }
}
