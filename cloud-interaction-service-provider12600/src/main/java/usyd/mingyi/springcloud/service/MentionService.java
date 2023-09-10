package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.Mention;

public interface MentionService extends IService<Mention> {
    Page<Mention> getAllMentionList(Long userId, Long current, Integer pageSize);
    void markMentionAsRead(Long userId,Long mentionId);

    Long countMentionsReceived(Long userId);
}
