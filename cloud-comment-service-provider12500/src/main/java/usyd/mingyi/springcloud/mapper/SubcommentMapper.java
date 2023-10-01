package usyd.mingyi.springcloud.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import usyd.mingyi.common.pojo.Subcomment;


@Mapper
public interface SubcommentMapper extends MPJBaseMapper<Subcomment> {

    @Update("UPDATE subcomment SET subcomment_love = subcomment_love + #{delta} WHERE subcomment_id = #{subcommentId}")
        //并不是很推荐注解 感觉移植性太差了  尤其对于Mybatis这种半自动化 JPA 这里我就偷个懒就这样写写算了
    int updateSubcommentLove(@Param("subcommentId") Long subcommentId, @Param("delta") int delta);
}
