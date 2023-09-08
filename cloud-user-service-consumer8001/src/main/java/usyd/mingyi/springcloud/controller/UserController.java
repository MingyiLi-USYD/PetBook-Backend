package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.dto.UserDto;
import usyd.mingyi.springcloud.pojo.FriendRequest;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.FriendServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.FieldUtils;
import usyd.mingyi.springcloud.utils.ResultHandler;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    FriendServiceFeign friendServiceFeign;
    @GetMapping("/user/{userId}")
    public R<User> getUser(@PathVariable("userId") Long userId){
      return   userServiceFeign.getUserById(userId);
    }

    @GetMapping("/user/init")
    public R<UserDto> initUserInfo() {


       // R<User> currentUser = userServiceFeign.getCurrentUser();
        User currentUser = userServiceFeign.getCurrentUser();
       // R<List<FriendRequest>> resFriendRequest = friendServiceFeign.getFriendRequestList();
        System.out.println(currentUser);
        R<List<Friendship>> resFriendship = friendServiceFeign.getFriendshipList();
        List<Friendship> friendshipList = resFriendship.getData();
        List<Long> userIds = FieldUtils.extractField(friendshipList, Friendship::getFriendId);
        List<User>  userList = userServiceFeign.getUserListByIds(userIds);

        List<FriendshipDto> friendshipDtos = ResultHandler.mergeObjectLists(friendshipList,
                userList,
                FriendshipDto::setFriendInfo,
                FriendshipDto::getFriendId,
                FriendshipDto::new,
                User::getUserId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(currentUser,userDto);
        userDto.setFriendshipDtoList(friendshipDtos);
        return R.success(userDto);
    }



}
