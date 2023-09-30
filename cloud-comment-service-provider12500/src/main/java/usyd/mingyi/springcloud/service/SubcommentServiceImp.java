package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;
import usyd.mingyi.common.pojo.Subcomment;
import usyd.mingyi.springcloud.mapper.SubcommentMapper;

import java.util.List;


@Service
public class SubcommentServiceImp extends ServiceImpl<SubcommentMapper, Subcomment> implements SubcommentService {

    @Override
    public List<Subcomment> getSubcomments(Long commentId, Boolean limit) {
        LambdaQueryWrapper<Subcomment> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Subcomment::getCommentId, commentId).last(limit, "LIMIT 3");
        return this.list(wrapper);
    }
}
