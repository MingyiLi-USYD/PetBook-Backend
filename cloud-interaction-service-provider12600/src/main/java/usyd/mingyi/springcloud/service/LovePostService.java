package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.LovePost;

import java.util.List;

public interface LovePostService extends IService<LovePost> {
    Page<LovePost> getLovePostsToMe(Long userId, Long current, Integer pageSize);

    List<LovePost> getLovePosts(Long userId);

    void markLovePostRead(Long userId, Long lovePostId);
    Long countLovePostsReceived(Long userId);

    Boolean lovePost(Long userId,Long postId,Long postUserId);
    void cancelLovePost(Long userId,Long postId,Long postUserId);

}
