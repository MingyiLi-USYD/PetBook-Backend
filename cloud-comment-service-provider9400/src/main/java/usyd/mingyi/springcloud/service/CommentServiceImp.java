package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.CommentMapper;
import usyd.mingyi.springcloud.pojo.Comment;


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
       // this.page(page,new LambdaQueryWrapper<Comment>().eq(Comment::getp,postId));
        return page;
    }
}
