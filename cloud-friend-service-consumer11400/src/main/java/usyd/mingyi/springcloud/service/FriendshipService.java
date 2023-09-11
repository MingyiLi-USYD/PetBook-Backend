package usyd.mingyi.springcloud.service;


import usyd.mingyi.springcloud.dto.FriendshipDto;

import java.util.List;

public interface FriendshipService {

    List<FriendshipDto> getAllFriends();//根据用户id获取用户所有的friends
    List<FriendshipDto> getAllFriends(Long [] ids);//根据用户id获取用户所有的friends
/*    int checkFriendshipStatus(Long fromId, Long toId);
    void deleteUser(Long fromId,Long toId);
    void deleteUserSyncSocket(Long fromId,Long toId);

    FriendshipDto getFriendshipByIds(Long userId, Long friendId);

    Boolean isFriend(Long userId,Long friendId);*/
}
