package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.FriendRequestHandler;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.FriendRequestDto;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.entity.ServiceMessage;
import usyd.mingyi.springcloud.entity.ServiceMessageType;
import usyd.mingyi.springcloud.pojo.FriendRequest;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.ChatServiceFeign;
import usyd.mingyi.springcloud.service.FriendServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class FriendRequestController {
    @Autowired
    FriendServiceFeign friendServiceFeign;
    @Autowired
    FriendRequestHandler friendRequestHandler;
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    ChatServiceFeign chatServiceFeign;
    @Autowired
    PoConvertToDto poConvertToDto;

    @PostMapping("/friendRequest/{id}")
    public R<String> sendFriendRequest(@PathVariable("id") Long toId, @RequestParam("msg") String msg) {
        String res = friendServiceFeign.sendFriendRequest(toId, msg);
        Long currentId = BaseContext.getCurrentId();
        ServiceMessage serviceMessage = new ServiceMessage(currentId,System.currentTimeMillis(),
                toId, ServiceMessageType.ADD_FRIEND);
        chatServiceFeign.sendServiceMessage(serviceMessage);
        return R.success(res);
    }

    @GetMapping("/friendRequest/{id}")
    public R<FriendshipDto> approveFriendRequest(@PathVariable("id") Long toId) {
        Friendship friendship = friendServiceFeign.approveFriendRequest(toId);
        Long friendId = friendship.getFriendId();
        User userById = userServiceFeign.getUserById(friendId);
        FriendshipDto friendshipDto = poConvertToDto.friendshipToFriendshipDto(friendship);
        friendshipDto.setFriendInfo(userById);
        Long currentId = BaseContext.getCurrentId();
        ServiceMessage serviceMessage = new ServiceMessage(currentId,System.currentTimeMillis(),
                toId, ServiceMessageType.AGREE_ADD_FRIEND);
        chatServiceFeign.sendServiceMessage(serviceMessage);
        return R.success(friendshipDto);
    }
    @DeleteMapping("/friendRequest/{id}")
    public R<String> rejectFriendRequest(@PathVariable("id") Long toId) {
        String res = friendServiceFeign.rejectFriendRequest(toId);
        Long currentId = BaseContext.getCurrentId();
        ServiceMessage serviceMessage = new ServiceMessage(currentId,System.currentTimeMillis(),
                toId, ServiceMessageType.REJECT_ADD_FRIEND);
        chatServiceFeign.sendServiceMessage(serviceMessage);
        return R.success(res);
    }

    @GetMapping("/friendRequests")
    public R<List<FriendRequestDto>> getAllRequests(){
        List<FriendRequest> friendRequestList = friendServiceFeign.getFriendRequestList();
        List<FriendRequestDto> res = friendRequestHandler.convert(friendRequestList);
        return R.success(res);
    }
    @PostMapping("/friendRequests/byIds")
    public R<List<FriendRequestDto>> getAllRequestsByIds(@RequestBody Long[] ids){
        List<FriendRequest> friendRequestListByIds = friendServiceFeign.getFriendRequestListByIds(ids);
        List<FriendRequestDto> res = friendRequestHandler.convert(friendRequestListByIds);
        return R.success(res);
    }
}
