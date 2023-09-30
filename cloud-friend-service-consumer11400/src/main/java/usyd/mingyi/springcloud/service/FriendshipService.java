package usyd.mingyi.springcloud.service;



import usyd.mingyi.common.dto.FriendshipDto;

import java.util.List;

public interface FriendshipService {

    List<FriendshipDto> getAllFriends();//根据用户id获取用户所有的friends
    List<FriendshipDto> getAllFriends(Long [] ids);//根据用户id获取用户所有的friends

}
