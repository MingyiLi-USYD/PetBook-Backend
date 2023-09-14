package usyd.mingyi.springcloud.common;

import usyd.mingyi.springcloud.dto.CommentDto;
import usyd.mingyi.springcloud.dto.SubcommentDto;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.utils.ResultHandler;

import java.util.List;

public class SubcommentHandler {
    public static List<SubcommentDto> handleUserInfo(List<Subcomment> subcommentList, List<User> userList) {
        return ResultHandler.mergeObjectLists(subcommentList,
                userList,
                SubcommentDto::setSubcommentUser,
                SubcommentDto::getUserId,
                SubcommentDto::new,
                User::getUserId);
    }

}
