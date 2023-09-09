package usyd.mingyi.springcloud.common;

import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.utils.ResultHandler;

import java.util.List;

public class FriendshipHandler {
   public static List<FriendshipDto> handleUserInfo(List<Friendship> friendshipList, List<User> userList){
      return ResultHandler.mergeObjectLists(friendshipList,
               userList,
               FriendshipDto::setFriendInfo,
               FriendshipDto::getFriendId,
               FriendshipDto::new,
               User::getUserId);
   }
}
