package usyd.mingyi.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.FriendRequest;
import usyd.mingyi.springcloud.pojo.Friendship;

import java.util.List;
@FeignClient(value = "friend-service-provider")
public interface FriendServiceFeign {
    @GetMapping("/friends/status/{id}")
    R<Integer> getFriendshipStatus(@PathVariable("id") Long toId);

    @DeleteMapping("/friend/{id}")
    R<String> deleteFriendship(@PathVariable("id") Long toId);

    @GetMapping("/friends")
    R<List<Friendship>> getFriendshipList();

    @PostMapping("/friends/byIds")
    R<List<Friendship>> getFriendshipListByIds(@RequestBody Long[] ids);

    @GetMapping("/friendRequests")
    R<List<FriendRequest>> getFriendRequestList();

    @PostMapping("/friendRequests/byIds")
    R<List<FriendRequest>> getFriendRequestListByIds(@RequestBody Long[] ids);

    @PostMapping("/friendRequest/{id}")
    R<String> sendFriendRequest(@PathVariable("id") Long toId, @RequestParam("msg") String msg);

    @GetMapping("/friendRequest/{id}")
    R<Friendship> approveFriendRequest(@PathVariable("id") Long toId);

    @DeleteMapping("/friendRequest/{id}")
    R<String> rejectFriendRequest(@PathVariable("id") Long toId);
}
