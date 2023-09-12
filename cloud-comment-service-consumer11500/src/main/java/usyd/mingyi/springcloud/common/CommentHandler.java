package usyd.mingyi.springcloud.common;

import org.springframework.stereotype.Component;
import usyd.mingyi.springcloud.dto.CommentDto;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.utils.FieldUtils;
import usyd.mingyi.springcloud.utils.ResultHandler;

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
