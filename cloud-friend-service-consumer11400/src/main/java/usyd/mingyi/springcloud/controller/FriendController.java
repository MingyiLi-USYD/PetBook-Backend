package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.dto.FriendshipDto;
import usyd.mingyi.common.entity.ServiceMessage;
import usyd.mingyi.common.entity.ServiceMessageType;
import usyd.mingyi.common.feign.ChatServiceFeign;
import usyd.mingyi.common.feign.FriendServiceFeign;
import usyd.mingyi.common.pojo.Friendship;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.common.FriendshipHandler;


import java.util.List;

@RestController
@Slf4j
public class FriendController {
    @Autowired
    FriendServiceFeign friendServiceFeign;
    @Autowired
    FriendshipHandler friendshipHandler;
    @Autowired
    ChatServiceFeign chatServiceFeign;


    @GetMapping("/friend/status/{id}")
    public R<Integer> getFriendshipStatus(@PathVariable("id") Long toId) {
        return R.success(friendServiceFeign.getFriendshipStatus(toId));
    }


    @GetMapping("/friends")
    public R<List<FriendshipDto>> getFriendsList() {
        List<Friendship> friendshipList = friendServiceFeign.getFriendshipList();
        return R.success( friendshipHandler.convert(friendshipList));

    }

    @PostMapping("/friends/byIds")
    public R<List<FriendshipDto>> getFriendsListByIds(@RequestBody Long[] ids) {
        List<Friendship> friendshipList = friendServiceFeign.getFriendshipListByIds(ids);
        return R.success( friendshipHandler.convert(friendshipList));
    }

    @DeleteMapping("/friend/{id}")
    public R<String> deleteFriendFromList(@PathVariable("id") Long toId) {
        friendServiceFeign.deleteFriendship(toId);
        Long currentId = BaseContext.getCurrentId();
        ServiceMessage serviceMessage = new ServiceMessage(currentId,System.currentTimeMillis(),
                toId, ServiceMessageType.DELETE_FRIEND);
        chatServiceFeign.sendServiceMessage(serviceMessage);
        return R.success("删除成功");
    }



}
