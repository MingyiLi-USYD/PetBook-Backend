package usyd.mingyi.springcloud.common;

import org.springframework.stereotype.Component;
import usyd.mingyi.common.dto.CommentDto;
import usyd.mingyi.common.pojo.Comment;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.utils.ResultHandler;

import java.util.List;

@Component
public class CommentHandler {
    public static List<CommentDto> handleUserInfo(List<Comment> commentList, List<User> userList) {
        return ResultHandler.mergeObjectLists(commentList,
                userList,
                CommentDto::setCommentUser,
                CommentDto::getUserId,
                CommentDto::new,
                User::getUserId);
    }

}
