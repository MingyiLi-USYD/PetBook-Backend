package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.common.pojo.Comment;
import usyd.mingyi.common.pojo.Subcomment;


public interface CommentService extends IService<Comment> {
    Page<Comment> getCommentsByPostId(Long currPage, Integer pageSize, Long postId);
    Page<Comment> getCommentsToMe(Long userId,Long current,Integer pageSize);

    void markAsRead(Long commentId,Long userId);
    void saveSubcommentAndMarkAsRead(Subcomment subcomment);

    void increaseLoveOfComment(Long commentId);
    void decreaseLoveOfComment(Long commentId);


}
