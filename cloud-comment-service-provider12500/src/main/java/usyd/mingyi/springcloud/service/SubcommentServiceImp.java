package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.pojo.Subcomment;
import usyd.mingyi.springcloud.mapper.SubcommentMapper;

import java.util.List;


@Service
public class SubcommentServiceImp extends ServiceImpl<SubcommentMapper, Subcomment> implements SubcommentService {
    @Autowired
    SubcommentMapper subcommentMapper;

    @Override
    public List<Subcomment> getSubcomments(Long commentId, Boolean limit) {
        LambdaQueryWrapper<Subcomment> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Subcomment::getCommentId, commentId).last(limit, "LIMIT 3");
        return this.list(wrapper);
    }

    @Override
    public void increaseLoveOfSubcomment(Long subcommentId) {
        if (subcommentMapper.updateSubcommentLove(subcommentId,1)==0) {
            throw new CustomException("点赞失败");
        }
    }

    @Override
    public void decreaseLoveOfSubcomment(Long subcommentId) {
        if (subcommentMapper.updateSubcommentLove(subcommentId,-1)==0) {
            throw new CustomException("取消点赞失败");
        }
    }
}
