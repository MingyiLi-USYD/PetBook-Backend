package usyd.mingyi.springcloud.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import usyd.mingyi.common.pojo.Comment;


@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    @Update("UPDATE comment SET comment_love = comment_love + #{delta} WHERE comment_id = #{commentId}")
    //并不是很推荐注解 感觉移植性太差了  尤其对于Mybatis这种半自动化 JPA 这里我就偷个懒就这样写写算了
    int updateCommentLove(@Param("commentId") Long commentId, @Param("delta") int delta);




}
