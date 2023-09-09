package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.service.FriendshipService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class FriendshipController {
    @Autowired
    FriendshipService friendService;

    @GetMapping("/friends/status/{id}")
    public R<Integer> getFriendshipStatus(@PathVariable("id") Long toId) {
        Long fromId = BaseContext.getCurrentId();
        if (fromId.equals(toId)) {
            return R.success(0);
        }
        return R.success(friendService.checkFriendshipStatus(fromId, toId));
    }

    @DeleteMapping("/friend/{id}")
    public R<String> deleteFriendship(@PathVariable("id") Long toId) {
        Long currentId = BaseContext.getCurrentId();
        friendService.deleteUser(currentId, toId);
        return R.success("Successfully delete");
    }

    @GetMapping("/friends")
    public R<List<Friendship>> getFriendshipList() {
        Long currentId = BaseContext.getCurrentId();
        List<Friendship> allFriends = friendService.getAllFriends(currentId);
        return R.success(allFriends);
    }

    @PostMapping("/friends/byIds")
    public R<List<Friendship>> getFriendshipListByIds(@RequestBody Long[] ids) {
        Long currentId = BaseContext.getCurrentId();
        List<Friendship> allFriends = friendService.getAllFriends(currentId,ids);
        return R.success(allFriends);
    }

}
