package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.FriendshipHandler;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.FriendServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.FieldUtils;

import java.util.List;

@RestController
@Slf4j
public class FriendController {
    @Autowired
    FriendServiceFeign friendServiceFeign;
    @Autowired
    UserServiceFeign userServiceFeign;
    @GetMapping("/friends/status/{id}")
    public R<Integer> getFriendshipStatus(@PathVariable("id") Long toId) {
        return R.success(friendServiceFeign.getFriendshipStatus(toId));
    }


    @GetMapping("/friends")
    public R<List<FriendshipDto>> getFriendsList() {
        List<Friendship> friendshipList = friendServiceFeign.getFriendshipList();
        return getListR(friendshipList);
    }

    @PostMapping("/friends/byIds")
    public R<List<FriendshipDto>> getFriendsListByIds(@RequestBody Long[] ids) {
        List<Friendship> friendshipList = friendServiceFeign.getFriendshipListByIds(ids);
        return getListR(friendshipList);
    }

    @DeleteMapping("/friends/{id}")
    public R<String> deleteFriendFromList(@PathVariable("id") Long toId) {

        return R.success(friendServiceFeign.deleteFriendship(toId));
    }

    @NotNull
    private R<List<FriendshipDto>> getListR(List<Friendship> friendshipList) {
        List<Long> users = FieldUtils.extractField(friendshipList, Friendship::getFriendId,true);
        List<User> userListByIds = userServiceFeign.getUserListByIds(users);
        List<FriendshipDto> friendshipDtos = FriendshipHandler.handleUserInfo(friendshipList, userListByIds);
        return R.success(friendshipDtos);
    }

}
