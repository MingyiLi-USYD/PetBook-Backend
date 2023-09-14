package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Subcomment;

public interface CommentService extends IService<Comment> {
    Page<Comment> getCommentsByPostId(Long currPage, Integer pageSize, Long postId);
    Page<Comment> getCommentsToMe(Long userId,Long current,Integer pageSize);

    void markAsRead(Long commentId,Long userId);
    void saveSubcommentAndMarkAsRead(Subcomment subcomment);

    //void saveSubcommentAndMarkAsRead(Subcomment subcomment);
/*
    CommentDto saveAndGet(Comment comment);

    Page<CommentDto> getAllComments(Long userId,Long current,Integer pageSize);





    Integer countCommentsReceived(Long userId);
*/

}
