package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.mapper.CommentMapper;
import usyd.mingyi.springcloud.mapper.SubcommentMapper;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Subcomment;


@Service
@Slf4j
public class CommentServiceImp extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    SubcommentMapper subcommentMapper;


    @Override
    public Page<Comment> getCommentsByPostId(Long currPage, Integer pageSize, Long postId) {
        Page<Comment> page = new Page<>(currPage, pageSize);
        this.page(page, new LambdaQueryWrapper<Comment>().eq(Comment::getPostId, postId));
        return page;
    }

    @Override
    public Page<Comment> getCommentsToMe(Long userId, Long current, Integer pageSize) {
        Page<Comment> page = new Page<>(current, pageSize);
        this.page(page, new LambdaQueryWrapper<Comment>().eq(Comment::getTargetUserId, userId));
        return page;
    }

    @Override
    public void markAsRead(Long commentId, Long userId) {
        Comment comment = getById(commentId);
        if (comment == null) {
            throw new CustomException("No comment found");
        }
        if (!comment.getTargetUserId().equals(userId)) {
            throw new CustomException("没有权限");
        }
        comment.setIsRead(true);
        this.updateById(comment);
    }

    @Override
    @Transactional
    public void saveSubcommentAndMarkAsRead(Subcomment subcomment) {
        if (subcommentMapper.insert(subcomment)==0) {
            throw new CustomException("插入子评论失败");
        }
        this.markAsRead(subcomment.getCommentId(),subcomment.getUserId());
    }



}
