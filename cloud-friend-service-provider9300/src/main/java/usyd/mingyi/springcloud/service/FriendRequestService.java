package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.FriendRequest;

import java.util.List;

public interface FriendRequestService extends IService<FriendRequest> {
    void sendRequest(Long fromId,Long toId,String msg);

    List<FriendRequest> getAllRequests(Long userId);
    List<FriendRequest> getAllRequests(Long userId,Long[] ids);


    void approveRequest(Long userId,Long approvedUserId);
    void rejectRequest(Long userId,Long approvedUserId);
    void addUserToFriendList(Long userId,Long approvedUserId);

    List<FriendRequest> getAllRequestsAndMarkRead(Long userId);
    List<FriendRequest> getAllRequestsAndMarkRead(Long userId,Long[] ids);

/*
    void sendRequestSyncSocket(Long fromId, Long toId, String msg);
    FriendshipDto approveRequestAndGetSyncSocket(Long userId, Long approvedUserId);
    void rejectRequestSyncSocket(Long userId, Long targetUserId);
*/

}
