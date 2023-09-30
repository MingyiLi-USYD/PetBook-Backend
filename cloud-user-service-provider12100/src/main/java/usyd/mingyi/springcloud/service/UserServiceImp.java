package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.springcloud.mapper.UserMapper;


@Service
@Slf4j
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public Page<User> getPageUserList(Long current, Long size, String keywords) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.
                like(keywords != null && !keywords.isEmpty(), User::getUsername, keywords);
        return this.page(new Page<>(current, size), query);
    }
}
