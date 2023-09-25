package usyd.mingyi.springcloud.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.User;

public interface UserService extends IService<User> {



    User getUserByUsername(String username);



}
