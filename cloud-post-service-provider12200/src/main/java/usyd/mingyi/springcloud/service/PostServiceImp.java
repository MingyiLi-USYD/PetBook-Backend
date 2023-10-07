package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.mapper.PostMapper;


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
                 .like(keyword!=null,Post::getPostContent,keyword)
                 .orderByDesc(order == 1, Post::getPostTime)
                 .orderByDesc(order == 2, Post::getLove));

    }

    @Override
    public List<Post> getPostsByUserIdVisible(Long userId) {
        return this.list(new LambdaQueryWrapper<Post>()
                .eq(Post::getUserId,userId)
                .eq(!BaseContext.getCurrentId().equals(userId),Post::getVisible,true));
    }

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<Post>().eq(Post::getUserId,userId));
    }

    @Override
    public List<Post> getPostsByIds(Collection<Long> postIds) {
        return this.listByIds(postIds);
    }

    @Override
    public Page<Post> getPostsByIdsWithPage(Collection<Long> postIds, Long current, Integer pageSize) {

        return this.page(new Page<>(current, pageSize),
                new LambdaQueryWrapper<Post>().in(Post::getPostId,postIds));

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED )
    public Post changeLoveNumber(Long postId, Integer delta){
        int maxRetries = 3;
        int retries = 0;
        while (retries < maxRetries) {
            Post post = this.getById(postId);
            if (post == null) {
                throw new CustomException("Not found post");
            }
            // Modify the post data
            post.setLove(post.getLove() + delta);

            boolean b = this.updateById(post);

            if (b) {
                return post; // Update succeeded
            }

            // Update failed, retry after a short sleep
            retries++;
            try {
                Thread.sleep(5); // Sleep for 5 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
        }

        // Max retries exceeded, throw an exception
        throw new CustomException("Failed to update post after multiple retries");
    }

}
