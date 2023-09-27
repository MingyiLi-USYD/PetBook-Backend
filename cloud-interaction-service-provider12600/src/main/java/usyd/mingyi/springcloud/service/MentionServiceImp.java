package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.mapper.MentionMapper;
import usyd.mingyi.springcloud.pojo.Mention;


@Service
public class MentionServiceImp extends ServiceImpl<MentionMapper, Mention> implements MentionService {


    @Override
    public Page<Mention> getAllMentionList(Long myId, Long current, Integer pageSize) {
        Page<Mention> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<Mention> query = new LambdaQueryWrapper<>();
        query
                .eq(Mention::getTargetUserId, myId)
                .eq(Mention::getIsRead, false)
                .orderByDesc(Mention::getCreateTime);
           return this.page(page,query);
    }

    @Override
    public void markMentionAsRead(Long userId, Long mentionId) {
        Mention mention = this.getById(mentionId);
        if(mention==null){
            throw new CustomException("没找到");
        }
        if (!mention.getTargetUserId().equals(userId)) {
            throw new CustomException("没权限修改");
        }
        if(!mention.getIsRead()){
            mention.setIsRead(true);
            this.updateById(mention);
        }

    }

    @Override
    public Long countMentionsReceived(Long userId) {
        LambdaQueryWrapper<Mention> query = new LambdaQueryWrapper<>();
        return (long) this.count(
                query.eq(Mention::getTargetUserId,userId)
                .eq(Mention::getIsRead, false)
        );
    }
}
