package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.mapper.CommentMapper;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Subcomment;


@Service
@Slf4j
public class CommentServiceImp extends ServiceImpl<CommentMapper, Comment>implements CommentService {



    @Override
    public IPage<Comment> getCommentsByPostId(Long currPage, Integer pageSize, Long postId) {
        IPage<Comment> page = new Page<>(currPage,pageSize);
        this.page(page,new LambdaQueryWrapper<Comment>().eq(Comment::getPostId,postId));
        return page;
    }

    @Override
    public IPage<Comment> getCommentsToMe(Long userId, Long current, Integer pageSize) {
        IPage<Comment> page = new Page<>(current,pageSize);
        this.page(page,new LambdaQueryWrapper<Comment>().eq(Comment::getTargetUserId,userId));
        return page;
    }

    @Override
    public void markAsRead(Long commentId,Long userId) {
        Comment comment = getById(commentId);
        if(comment==null){
            throw new CustomException("No comment found");
        }
        if(!comment.getTargetUserId().equals(userId)){
            throw new CustomException("没有权限");
        }
        comment.setIsRead(true);
        this.updateById(comment);
    }


}
