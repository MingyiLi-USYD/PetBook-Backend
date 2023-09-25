package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.UserMapper;
import usyd.mingyi.springcloud.pojo.User;


@Service
@Slf4j
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;



    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,username))  ;
    }
}