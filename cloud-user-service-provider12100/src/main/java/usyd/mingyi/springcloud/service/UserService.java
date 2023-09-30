package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.common.pojo.User;

public interface UserService extends IService<User> {

    Page<User> getPageUserList (Long current, Long size, String keywords);


}
