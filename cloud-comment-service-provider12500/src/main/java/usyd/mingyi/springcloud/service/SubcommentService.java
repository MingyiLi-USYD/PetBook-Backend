package usyd.mingyi.springcloud.service;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.Subcomment;

import java.util.List;

public interface SubcommentService extends IService<Subcomment> {
    List<Subcomment> getSubcomments(Long commentId, Boolean limit);
/*
    Integer getSubcommentsSize(Long commentId);
    SubcommentDto saveAndGet(SubcommentDto subcomment);
    void saveSubcomment(SubcommentDto subcommentDto);*/
}
