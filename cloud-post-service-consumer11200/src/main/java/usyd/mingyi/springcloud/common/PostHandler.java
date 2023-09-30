package usyd.mingyi.springcloud.common;


import usyd.mingyi.common.dto.PostDto;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.utils.ResultHandler;

import java.util.List;

public class PostHandler {
    public static List<PostDto> handleUserInfo(List<Post> postList, List<User> userList) {
        return ResultHandler.mergeObjectLists(postList,
                userList,
                PostDto::setPostUser,
                PostDto::getUserId,
                PostDto::new,
                User::getUserId);
    }
}
