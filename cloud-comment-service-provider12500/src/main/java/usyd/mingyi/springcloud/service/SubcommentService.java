package usyd.mingyi.springcloud.service;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.common.pojo.Subcomment;

import java.util.List;

public interface SubcommentService extends IService<Subcomment> {
    List<Subcomment> getSubcomments(Long commentId, Boolean limit);
    void increaseLoveOfSubcomment(Long subcommentId);
    void decreaseLoveOfSubcomment(Long subcommentId);

}
