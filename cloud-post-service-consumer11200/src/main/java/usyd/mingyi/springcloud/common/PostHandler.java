package usyd.mingyi.springcloud.common;

import usyd.mingyi.springcloud.dto.PostDto;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.utils.ResultHandler;

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
