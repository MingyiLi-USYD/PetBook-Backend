package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.FriendRequest;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.service.FriendRequestService;
import usyd.mingyi.springcloud.service.FriendshipService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class FriendRequestController {

    @Autowired
    FriendRequestService friendRequestService;
    @Autowired
    FriendshipService friendshipService;

    @GetMapping("/friendRequests")
    public R<List<FriendRequest>> getFriendRequestList(){
        Long currentId = BaseContext.getCurrentId();
        List<FriendRequest> allRequest = friendRequestService.getAllRequestsAndMarkRead(currentId);
        return R.success(allRequest);
    }
    @PostMapping("/friendRequests/byIds")
    public R<List<FriendRequest>> getFriendRequestListByIds(@RequestBody Long[] ids){
        Long currentId = BaseContext.getCurrentId();
        List<FriendRequest> allRequest = friendRequestService.getAllRequestsAndMarkRead(currentId,ids);
        return R.success(allRequest);
    }

    @PostMapping("/friendRequest/{id}")
    public R<String> sendFriendRequest(@PathVariable("id") Long toId, @RequestParam("msg") String msg) {
        Long currentId = BaseContext.getCurrentId();
        //friendRequestService.sendRequestSyncSocket(currentId, toId, msg);
        friendRequestService.sendRequest(currentId, toId, msg);
        return R.success("request have been sent");
    }



    @GetMapping("/friendRequest/{id}")
    public R<Friendship> approveFriendRequest(@PathVariable("id") Long toId) {
        Long currentId = BaseContext.getCurrentId();
        friendRequestService.approveRequest(currentId, toId);
        return R.success(friendshipService.getFriendship(currentId,toId));
    }

    @DeleteMapping("/friendRequest/{id}")
    public R<String> rejectFriendRequest(@PathVariable("id") Long toId) {
        Long currentId = BaseContext.getCurrentId();
      //  friendRequestService.rejectRequestSyncSocket(currentId, toId);
        friendRequestService.rejectRequest(currentId, toId);
        return R.success("Successfully reject");
    }
}
