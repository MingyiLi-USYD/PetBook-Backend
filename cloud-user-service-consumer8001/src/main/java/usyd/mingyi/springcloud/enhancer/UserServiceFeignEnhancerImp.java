/*
package usyd.mingyi.springcloud.enhancer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserServiceFeign;

import java.util.List;

@Component
public class UserServiceFeignEnhancerImp implements UserServiceFeignEnhancer {
    @Autowired
    UserServiceFeign userServiceFeign;
    @Override
    public User getUserById(Long userId) {
        return userServiceFeign.getUserById(userId).getData();
    }

    @Override
    public String updateProfile(User user) {
        return null;
    }

    @Override
    public List<User> getUserListByIds(List<Long> ids) {
        return null;
    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
*/
