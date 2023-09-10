package usyd.mingyi.springcloud.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;
import usyd.mingyi.springcloud.pojo.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface UserService extends IService<User> {

    Page<User>  getPageUserList ( Long current, Long size, String keywords);


 /*   User getUserByUsername(String username);


    String queryPassword(String username);

    void sendEmail(String email,String userName);

    UserDto getProfile(Long targetUserId);

    int updatePassword(String username,String password);

    User getBasicUserInfoById(Long id);
    User getBasicUserInfoById(String id);

    UserDto initUserInfo(Long id);
*/

}
