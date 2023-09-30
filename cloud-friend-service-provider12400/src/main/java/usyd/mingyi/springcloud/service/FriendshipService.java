package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.common.pojo.Friendship;

import java.util.List;

public interface FriendshipService extends IService<Friendship> {
    int checkFriendshipStatus(Long fromId, Long toId);
    void deleteUser(Long fromId,Long toId);
    List<Friendship> getAllFriends(Long id);//根据用户id获取用户所有的friends

    List<Friendship> getAllFriends(Long id,Long [] ids);//根据用户id获取用户所有的friends

    Boolean isFriend(Long userId,Long friendId);


    Friendship getFriendship(Long userId, Long friendId);


}
