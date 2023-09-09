package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.FriendRequestHandler;
import usyd.mingyi.springcloud.common.FriendshipHandler;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.dto.FriendRequestDto;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.dto.UserDto;
import usyd.mingyi.springcloud.pojo.FriendRequest;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.FriendServiceFeign;
import usyd.mingyi.springcloud.service.PostServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.BaseContext;
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
    @Autowired
    PostServiceFeign postServiceFeign;
    @GetMapping("/user/{userId}")
    public R<User> getUser(@PathVariable("userId") Long userId){

        User userById = userServiceFeign.getUserById(userId);
        return R.success(userById);
    }

    @GetMapping("/user/init")
    public R<UserDto> initUserInfo() {

        User currentUser = userServiceFeign.getCurrentUser();
        List<FriendRequest> friendRequestList = friendServiceFeign.getFriendRequestList();
        List<Friendship> friendshipList = friendServiceFeign.getFriendshipList();
        List<Post> postList = postServiceFeign.getMyPosts();


        List<Long> friendUserIds = FieldUtils.extractField(friendshipList, Friendship::getFriendId);
        List<User>  friendshipUserList = userServiceFeign.getUserListByIds(friendUserIds);
        List<FriendshipDto> friendshipDtos = FriendshipHandler.handleUserInfo(friendshipList, friendshipUserList);

        List<Long> requestUserIds = FieldUtils.extractField(friendRequestList, FriendRequest::getMyId);
        List<User>  friendRequestUserList= userServiceFeign.getUserListByIds(requestUserIds);
        List<FriendRequestDto> friendRequestDtos = FriendRequestHandler.handleUserInfo(friendRequestList, friendRequestUserList);




        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(currentUser,userDto);
        userDto.setFriendshipDtoList(friendshipDtos);
        userDto.setFriendRequestDtoList(friendRequestDtos);
        userDto.setPostList(postList);
        return R.success(userDto);
    }

    @GetMapping("/user/init2")
    public R<UserDto> promise() throws ExecutionException, InterruptedException {
        Long currentId = BaseContext.getCurrentId();

        // 同时发起3个Feign异步请求
        CompletableFuture<User> currentUserFuture = CompletableFuture.supplyAsync(() -> {
            BaseContext.setCurrentId(currentId);
            return userServiceFeign.getCurrentUser();
        });
        currentUserFuture.join();
        User user = currentUserFuture.get();
        System.out.println(user);

/*
        CompletableFuture<R<List<Friendship>>> friendshipFuture = CompletableFuture.supplyAsync(() -> friendServiceFeign.getFriendshipList());
        CompletableFuture<R<List<FriendRequest>>> friendRequestFuture = CompletableFuture.supplyAsync(() -> friendServiceFeign.getFriendRequestList());

        try {
            // 使用 CompletableFuture 的 join 方法等待所有请求完成
            currentUserFuture.join();
            friendshipFuture.join();
            friendRequestFuture.join();

            // 获取它们的结果
            User currentUser = currentUserFuture.get();
            R<List<Friendship>> resFriendship = friendshipFuture.get();
            R<List<FriendRequest>> userListByIds = friendRequestFuture.get();

            System.out.println(currentUser);
            System.out.println(resFriendship);
            System.out.println(userListByIds);
            // 在这里处理数据
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
*/

        return null;
    }



}
