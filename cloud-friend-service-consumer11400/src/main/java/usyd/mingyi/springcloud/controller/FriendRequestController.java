package usyd.mingyi.springcloud.controller;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.dto.FriendRequestDto;
import usyd.mingyi.common.dto.FriendshipDto;
import usyd.mingyi.common.entity.ServiceMessage;
import usyd.mingyi.common.entity.ServiceMessageType;
import usyd.mingyi.common.feign.ChatServiceFeign;
import usyd.mingyi.common.feign.FriendServiceFeign;
import usyd.mingyi.common.feign.UserServiceFeign;
import usyd.mingyi.common.pojo.FriendRequest;
import usyd.mingyi.common.pojo.Friendship;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.common.FriendRequestHandler;
import usyd.mingyi.springcloud.mapstruct.PoConvertToDto;

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
