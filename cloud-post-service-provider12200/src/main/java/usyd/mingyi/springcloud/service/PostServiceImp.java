package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.PostMapper;
import usyd.mingyi.springcloud.pojo.Post;

import java.util.Collection;
import java.util.List;


@Service
public class PostServiceImp extends ServiceImpl<PostMapper, Post> implements PostService {
    @Autowired
    PostMapper postMapper;


    @Override
    public Page<Post> getAllPosts(Long currPage, Integer pageSize, Integer order, String keyword) {
        Page<Post> page = new Page<>(currPage, pageSize);
         return   this.page(page,new LambdaQueryWrapper<Post>()
                 .eq(Post::getVisible,true)
                 .orderByDesc(order == 1, Post::getPostTime)
                 .orderByDesc(order == 2, Post::getLove));

    }

    @Override
    public List<Post> getPostsByUserIdVisible(Long userId) {
        return this.list(new LambdaQueryWrapper<Post>().eq(Post::getUserId,userId).eq(Post::getVisible,true));
    }

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<Post>().eq(Post::getUserId,userId));
    }

    @Override
    public List<Post> getPostsByIds(Collection<Long> postIds) {
        return this.listByIds(postIds);
    }


}
