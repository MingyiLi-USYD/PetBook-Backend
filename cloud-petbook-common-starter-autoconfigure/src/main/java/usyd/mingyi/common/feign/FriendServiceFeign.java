package usyd.mingyi.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.pojo.FriendRequest;
import usyd.mingyi.common.pojo.Friendship;
import java.util.List;

@FeignClient(value = "friend-service-provider", configuration = FeignConfig.class)
public interface FriendServiceFeign {
    @GetMapping("/friends/status/{id}")
    Integer getFriendshipStatus(@PathVariable("id") Long toId);

    @DeleteMapping("/friend/{id}")
    String deleteFriendship(@PathVariable("id") Long toId);

    @GetMapping("/friends")
    List<Friendship> getFriendshipList();

    @GetMapping("/friends/ids")
    List<Long> getFriendshipIdList();

    @PostMapping("/friends/byIds")
    List<Friendship> getFriendshipListByIds(@RequestBody Long[] ids);

    @GetMapping("/friendRequests")
    List<FriendRequest> getFriendRequestList();

    @PostMapping("/friendRequests/byIds")
    List<FriendRequest> getFriendRequestListByIds(@RequestBody Long[] ids);

    @PostMapping("/friendRequest/{id}")
    String sendFriendRequest(@PathVariable("id") Long toId, @RequestParam("msg") String msg);

    @GetMapping("/friendRequest/{id}")
    Friendship approveFriendRequest(@PathVariable("id") Long toId);

    @DeleteMapping("/friendRequest/{id}")
    String rejectFriendRequest(@PathVariable("id") Long toId);
}
