package usyd.mingyi.springcloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usyd.mingyi.common.pojo.User;


@Mapper
public interface UserMapper extends BaseMapper<User> {

}
