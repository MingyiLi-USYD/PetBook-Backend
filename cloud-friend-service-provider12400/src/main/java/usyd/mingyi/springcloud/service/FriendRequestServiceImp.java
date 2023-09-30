package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.pojo.FriendRequest;
import usyd.mingyi.common.pojo.Friendship;
import usyd.mingyi.springcloud.mapper.FriendRequestMapper;
import usyd.mingyi.springcloud.mapper.FriendshipMapper;

import java.util.List;


@Service
public class FriendRequestServiceImp extends ServiceImpl<FriendRequestMapper, FriendRequest> implements FriendRequestService {
    @Autowired
    FriendRequestMapper friendRequestMapper;
    @Autowired
    FriendshipMapper friendshipMapper;

    @Override
    @Transactional
    public void sendRequest(Long userId, Long targetUserId, String msg) {
        //当发现对方之前已经发生给好友请求 并且还在你的好友请求记录中 立刻成为好友
        LambdaQueryWrapper<FriendRequest> query = new LambdaQueryWrapper<>();
        query
                .eq(FriendRequest::getMyId, targetUserId)
                .eq(FriendRequest::getFriendId, userId);
        FriendRequest existRequest = friendRequestMapper.selectOne(query);
        if (existRequest != null) {
            friendRequestMapper.deleteById(existRequest.getRequestId());
            addUserToFriendList(userId, targetUserId);
            addUserToFriendList(targetUserId, userId);
            return;
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setMyId(userId);
        friendRequest.setFriendId(targetUserId);
        friendRequest.setMsg(msg);
        friendRequestMapper.insert(friendRequest);
    }

    @Override
    public List<FriendRequest> getAllRequests(Long userId) {

        return this.getAllRequests(userId, null);
    }

    @Override
    public List<FriendRequest> getAllRequests(Long userId, Long[] ids) {
        MPJLambdaWrapper<FriendRequest> query = new MPJLambdaWrapper<>();
        query.selectAll(FriendRequest.class)
                .eq(FriendRequest::getFriendId, userId)
                .in(ids != null, FriendRequest::getMyId, ids);
        return friendRequestMapper.selectList(query);
    }


    @Override
    @Transactional
    public void approveRequest(Long userId, Long approvedUserId) {
        LambdaQueryWrapper<FriendRequest> query = new LambdaQueryWrapper<>();
        query.eq(FriendRequest::getMyId, approvedUserId)
                .eq(FriendRequest::getFriendId, userId);
        FriendRequest friendRequest = friendRequestMapper.selectOne(query);
        if (friendRequest == null) {
            //没有发现此请求记录
            throw new CustomException("Not found such friend request in your request list");
        } else {
            //存在好友请求  删除请求并且互相添加为好友
            friendRequestMapper.deleteById(friendRequest.getRequestId());
            addUserToFriendList(userId, approvedUserId);
            addUserToFriendList(approvedUserId, userId);
        }
    }

    @Override
    public void rejectRequest(Long userId, Long approvedUserId) {
        LambdaQueryWrapper<FriendRequest> query = new LambdaQueryWrapper<>();
        query.eq(FriendRequest::getMyId, approvedUserId)
                .eq(FriendRequest::getFriendId, userId);
        FriendRequest existRequest = friendRequestMapper.selectOne(query);
        if (existRequest == null) {
            throw new CustomException("Not found such friend request in your request list");
        } else {
            friendRequestMapper.deleteById(existRequest.getRequestId());
        }
    }

    @Override
    public void addUserToFriendList(Long userId, Long approvedUserId) {
        Friendship friendship = new Friendship();
        friendship.setMyId(userId);
        friendship.setFriendId(approvedUserId);
        friendshipMapper.insert(friendship);
    }

    @Override
    @Transactional
    public List<FriendRequest> getAllRequestsAndMarkRead(Long userId) {
        return this.getAllRequestsAndMarkRead(userId, null);
    }

    @Override
    public List<FriendRequest> getAllRequestsAndMarkRead(Long userId, Long[] ids) {
        this.update(new LambdaUpdateWrapper<FriendRequest>()
                .set(FriendRequest::getIsRead, true)
                .eq(FriendRequest::getFriendId, userId));
        return this.getAllRequests(userId, ids);
    }
}
