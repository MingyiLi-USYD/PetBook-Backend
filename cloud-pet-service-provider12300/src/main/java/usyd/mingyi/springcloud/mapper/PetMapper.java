package usyd.mingyi.springcloud.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import usyd.mingyi.springcloud.pojo.Pet;

@Mapper
public interface PetMapper extends MPJBaseMapper<Pet> {
}
