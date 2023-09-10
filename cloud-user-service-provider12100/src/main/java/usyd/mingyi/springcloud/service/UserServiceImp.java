package usyd.mingyi.springcloud.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.UserMapper;
import usyd.mingyi.springcloud.pojo.User;

import java.util.Collection;
import java.util.List;


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
