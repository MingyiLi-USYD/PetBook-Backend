package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.pojo.Post;

import java.util.Collection;
import java.util.List;

public interface PostService extends IService<Post>  {
    Page<Post> getAllPosts(Long currPage, Integer pageSize, Integer order, String keyword);

    List<Post> getPostsByUserIdVisible(Long userId);
    List<Post> getPostsByUserId(Long userId);

    List<Post> getPostsByIds(Collection<Long> postIds);

    Post changeLoveNumber(Long postId, Integer delta);




}
