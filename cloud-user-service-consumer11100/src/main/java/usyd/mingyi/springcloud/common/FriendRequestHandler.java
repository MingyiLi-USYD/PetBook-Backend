package usyd.mingyi.springcloud.common;

import usyd.mingyi.springcloud.dto.FriendRequestDto;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.pojo.FriendRequest;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.utils.ResultHandler;

import java.util.List;

public class FriendRequestHandler {
    public static List<FriendRequestDto> handleUserInfo(List<FriendRequest> friendRequestList, List<User> userList){
        return ResultHandler.mergeObjectLists(friendRequestList,
                userList,
                FriendRequestDto::setFriendInfo,
                FriendRequestDto::getFriendInfo,
                FriendRequestDto::new,
                User::getUserId);
    }
}
