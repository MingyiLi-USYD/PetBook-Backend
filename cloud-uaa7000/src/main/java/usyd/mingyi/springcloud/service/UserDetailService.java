package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import usyd.mingyi.springcloud.authority.AuthorityConfigure;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.config.CustomUserDetails;
import usyd.mingyi.springcloud.mapper.UserMapper;
import usyd.mingyi.springcloud.pojo.User;

@Component
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AuthorityConfigure authorityConfigure;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username));
        if(user==null){
            return null;
        }

        return new CustomUserDetails(user,authorityConfigure.generate(user));
    }
}
