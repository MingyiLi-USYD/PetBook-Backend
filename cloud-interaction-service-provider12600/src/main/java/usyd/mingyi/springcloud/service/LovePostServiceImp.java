package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.mapper.LovePostMapper;
import usyd.mingyi.springcloud.pojo.LovePost;

import java.util.List;

@Service
public class LovePostServiceImp extends ServiceImpl<LovePostMapper, LovePost> implements LovePostService {


    @Override
    public Page<LovePost> getLovePostsToMe(Long userId, Long current, Integer pageSize) {
        Page<LovePost> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<LovePost> query = new LambdaQueryWrapper<>();
        query.eq(LovePost::getIsRead,false)
                .ne(LovePost::getUserId,userId)
                .eq(LovePost::getPostUserId,userId)
                .orderByDesc(LovePost::getCreateTime);
        return this.page(page,query);
    }

    @Override
    public List<LovePost> getLovePosts(Long userId) {
        return this.list(new LambdaQueryWrapper<LovePost>()
                .eq(LovePost::getUserId,userId)
                .eq(LovePost::getIsCanceled,false));
    }

    @Override
    public void markLovePostRead(Long userId, Long lovePostId) {
        //下面看起来有点繁琐 不如一个修改SQL来得方便根据返回的改动行来判断是否修改成功
        //但是下面操作可以细化错误原因 并且都是走索引效率不会有什么差别
        LovePost lovePost = this.getById(lovePostId);
        if (lovePost == null) {
            throw new CustomException("Never found this lovePost");
        }
        if (lovePost.getIsRead()) {
            throw new CustomException("Already read");
        }
        if (!lovePost.getPostUserId().equals(userId)) {
            throw new CustomException("No right to access");
        }

        lovePost.setIsRead(true);
        this.updateById(lovePost);
    }

    @Override
    public Long countLovePostsReceived(Long userId) {

       return (long) this.count(new LambdaQueryWrapper<LovePost>()
                .eq(LovePost::getIsRead,false)
               .eq(LovePost::getPostUserId,userId)
               .ne(LovePost::getUserId,userId));
    }

    @Override
    public Boolean lovePost(Long userId, Long postId, Long postUserId) {
        //返回值 表明是否是第一次点赞 用于决定是否socket通知
        LambdaQueryWrapper<LovePost> query = new LambdaQueryWrapper<>();
        query.eq(LovePost::getUserId,userId)
                .eq(LovePost::getPostId,postId)
                .eq(LovePost::getPostUserId,postUserId);
        LovePost exist = this.getOne(query);
        if(exist==null){
            //证明第一次点赞
            LovePost lovePost = new LovePost();
            lovePost.setUserId(userId);
            lovePost.setPostId(postId);
            lovePost.setPostUserId(postUserId);
            if (!this.save(lovePost)) {
                throw new CustomException("点赞失败");
            }else {
                return true;
            }
        }else {
            //证明不是第一次点赞了
            exist.setIsCanceled(false);
            if (this.updateById(exist)) {
                return false;
            }else {
                throw new CustomException("点赞失败");
            }
        }

    }

    @Override
    public void cancelLovePost(Long userId, Long postId, Long postUserId) {

        LambdaQueryWrapper<LovePost> query = new LambdaQueryWrapper<>();
        query.eq(LovePost::getUserId,userId)
                .eq(LovePost::getPostId,postId)
                .eq(LovePost::getPostUserId,postUserId);
        LovePost exist = this.getOne(query);

        if (exist == null) {
            throw new CustomException("never love this post before");
        } else {
            exist.setIsCanceled(true);
            this.updateById(exist);
        }
    }


}
