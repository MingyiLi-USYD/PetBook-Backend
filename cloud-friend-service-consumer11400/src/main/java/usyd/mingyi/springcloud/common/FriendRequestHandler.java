package usyd.mingyi.springcloud.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import usyd.mingyi.common.dto.FriendRequestDto;
import usyd.mingyi.common.feign.UserServiceFeign;
import usyd.mingyi.common.pojo.FriendRequest;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.utils.FieldUtils;
import usyd.mingyi.common.utils.ResultHandler;


import java.util.List;
@Component
public class FriendRequestHandler {
    @Autowired
    UserServiceFeign userServiceFeign;

    public static List<FriendRequestDto> handleUserInfo(List<FriendRequest> friendRequestList, List<User> userList) {
        return ResultHandler.mergeObjectLists(friendRequestList,
                userList,
                FriendRequestDto::setFriendInfo,
                FriendRequestDto::getMyId,
                FriendRequestDto::new,
                User::getUserId);
    }

    //下面是接收一个List<FriendRequest> 然后转化为List<FriendRequestDto>类型 并且查询数据库把Dto中的用户信息补齐

    public  List<FriendRequestDto> convert(List<FriendRequest> friendRequestList) {
        List<Long> requestUserIds = FieldUtils.extractField(friendRequestList, FriendRequest::getMyId);
        List<User>  friendRequestUserList= userServiceFeign.getUserListByIds(requestUserIds);
        return FriendRequestHandler.handleUserInfo(friendRequestList, friendRequestUserList);
    }
}
