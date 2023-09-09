package usyd.mingyi.springcloud.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.FieldUtils;
import usyd.mingyi.springcloud.utils.ResultHandler;

import java.util.List;

@Component
public class FriendshipHandler {
    @Autowired
    UserServiceFeign userServiceFeign;

    public static List<FriendshipDto> handleUserInfo(List<Friendship> friendshipList, List<User> userList) {
        return ResultHandler.mergeObjectLists(friendshipList,
                userList,
                FriendshipDto::setFriendInfo,
                FriendshipDto::getFriendId,
                FriendshipDto::new,
                User::getUserId);
    }

    public  List<FriendshipDto> convert(List<Friendship> friendshipList) {
        List<Long> friendUserIds = FieldUtils.extractField(friendshipList, Friendship::getFriendId);
        List<User>  friendshipUserList = userServiceFeign.getUserListByIds(friendUserIds);
        return FriendshipHandler.handleUserInfo(friendshipList, friendshipUserList);
    }
}
