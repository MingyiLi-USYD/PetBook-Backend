package usyd.mingyi.springcloud.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usyd.mingyi.common.pojo.Pet;


@Mapper
public interface PetMapper extends BaseMapper<Pet> {
}
