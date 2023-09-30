package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.common.pojo.User;


public interface UserService extends IService<User> {



    User getUserByUsername(String username);

    void sendEmail(String to,String code);

    void sendTempPassword(String to,String password);

}
