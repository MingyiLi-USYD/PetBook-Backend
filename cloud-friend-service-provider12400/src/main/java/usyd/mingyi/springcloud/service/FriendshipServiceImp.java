package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usyd.mingyi.common.pojo.FriendRequest;
import usyd.mingyi.common.pojo.Friendship;
import usyd.mingyi.springcloud.mapper.FriendRequestMapper;
import usyd.mingyi.springcloud.mapper.FriendshipMapper;


import java.util.List;

@Service
public class FriendshipServiceImp extends ServiceImpl<FriendshipMapper, Friendship> implements FriendshipService {
    @Autowired
    FriendshipMapper friendshipMapper;
    @Autowired
    FriendRequestMapper friendRequestMapper;


    @Override
    public int checkFriendshipStatus(Long fromId, Long toId) {
        LambdaQueryWrapper<Friendship> query = new LambdaQueryWrapper<>();
        query.eq(Friendship::getMyId, fromId)
                .eq(Friendship::getFriendId, toId);
        Friendship friendship = friendshipMapper.selectOne(query);
        if (friendship == null) {//证明还不是好友  现在判断是否有发送过好友请求
            LambdaQueryWrapper<FriendRequest> requestQuery = new LambdaQueryWrapper<>();
            requestQuery
                    .eq(FriendRequest::getMyId, fromId)
                    .eq(FriendRequest::getFriendId, toId);
            FriendRequest friendRequest = friendRequestMapper.selectOne(requestQuery);
            if (friendRequest != null) {
                return 2;//表示已经发送了好友请求 等待同意 pending状态
            } else {
                return 3; //表示不是好友 并且重来没有发送过好友请求
            }
        } else {
            return 1; //表示已经是好友了
        }

    }

    @Override
    @Transactional
    public void deleteUser(Long fromId, Long toId) {
        deleteUserInFriendList(fromId, toId);
        deleteUserInFriendList(toId, fromId);
    }

    @Override
    public List<Friendship> getAllFriends(Long userId) {
        return this.getAllFriends(userId,null);
    }


    @Override
    public List<Friendship> getAllFriends(Long userId, Long[] ids) {
        LambdaQueryWrapper<Friendship> query = new LambdaQueryWrapper<>();
        query
                .eq(Friendship::getMyId, userId)
                .in(ids!=null,Friendship::getFriendId,ids);
        return friendshipMapper.selectList(query);
    }


    public void deleteUserInFriendList(Long userId, Long deleteUserId) {
        LambdaQueryWrapper<Friendship> query = new LambdaQueryWrapper<>();
        query.eq(Friendship::getMyId,userId).eq(Friendship::getFriendId,deleteUserId);
        friendshipMapper.delete(query);
    }

    public Friendship getFriendship(Long userId, Long friendId){
        MPJLambdaWrapper<Friendship> query = new MPJLambdaWrapper<>();
        query
                .eq(Friendship::getMyId,userId)
                .eq(Friendship::getFriendId,friendId);
        return friendshipMapper.selectOne(query);

    }
    @Override
    public Boolean isFriend(Long userId, Long friendId) {
        return friendshipMapper.selectCount
                (new LambdaQueryWrapper<Friendship>()
                        .eq(Friendship::getMyId,userId)
                        .eq(Friendship::getFriendId,friendId))>0;
    }
}
