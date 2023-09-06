package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Subcomment;

public interface CommentService extends IService<Comment> {
    IPage<Comment> getCommentsByPostId(Long currPage, Integer pageSize, Long postId);
    IPage<Comment> getCommentsToMe(Long userId,Long current,Integer pageSize);

    void markAsRead(Long commentId,Long userId);

    //void saveSubcommentAndMarkAsRead(Subcomment subcomment);
/*
    CommentDto saveAndGet(Comment comment);

    Page<CommentDto> getAllComments(Long userId,Long current,Integer pageSize);



    void saveSubcommentAndMarkAsRead(SubcommentDto subcomment);
    void saveCommentAndMarkAsRead(Comment comment,Long mentionId);
    Integer countCommentsReceived(Long userId);
*/

}
