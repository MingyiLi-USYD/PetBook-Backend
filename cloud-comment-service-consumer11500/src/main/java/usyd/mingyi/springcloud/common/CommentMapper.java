package usyd.mingyi.springcloud.common;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import usyd.mingyi.springcloud.component.GenericMapper;
import usyd.mingyi.springcloud.dto.CommentDto;
import usyd.mingyi.springcloud.pojo.Comment;


@Mapper
public interface CommentMapper extends GenericMapper<Comment, CommentDto> {
    Page<CommentDto> convertPage(Page<Comment> commentPage);
}
