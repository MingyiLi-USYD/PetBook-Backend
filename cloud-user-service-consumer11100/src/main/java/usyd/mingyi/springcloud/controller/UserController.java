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
import usyd.mingyi.springcloud.pojo.*;
import usyd.mingyi.springcloud.service.FriendServiceFeign;
import usyd.mingyi.springcloud.service.PetServiceFeign;
import usyd.mingyi.springcloud.service.PostServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.task.Promise;
import usyd.mingyi.springcloud.utils.FieldUtils;

import java.util.List;
import java.util.concurrent.*;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    FriendServiceFeign friendServiceFeign;
    @Autowired
    PostServiceFeign postServiceFeign;
    @Autowired
    PetServiceFeign petServiceFeign;



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
        List<Pet> petList = petServiceFeign.getPetList();

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
        userDto.setPetList(petList);
        return R.success(userDto);
    }

    @GetMapping("/user/init2")
    public R<UserDto> promise() throws ExecutionException, InterruptedException {
/*        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> userServiceFeign.getCurrentUser(), customThreadPool);

        CompletableFuture<List<Friendship>> friendshipFuture = CompletableFuture.supplyAsync(() -> friendServiceFeign.getFriendshipList(),customThreadPool);

        CompletableFuture<List<FriendRequest>> friendRequestFuture = CompletableFuture.supplyAsync(() -> friendServiceFeign.getFriendRequestList(),customThreadPool);*/
       // CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(friendshipFuture, friendRequestFuture,userCompletableFuture);
        CompletableFuture<User> userCompletableFuture = Promise.buildTask(userServiceFeign::getCurrentUser);
        CompletableFuture<List<FriendshipDto>> friendshipDtoFuture = Promise.buildTask(() -> friendServiceFeign.getFriendshipList()).thenApply(friendshipList -> {
            List<Long> friendUserIds = FieldUtils.extractField(friendshipList, Friendship::getFriendId);
            List<User> friendshipUserList = userServiceFeign.getUserListByIds(friendUserIds);
            return FriendshipHandler.handleUserInfo(friendshipList, friendshipUserList);
        });

        CompletableFuture<List<FriendRequest>> friendRequestFuture = Promise.buildTask(() -> friendServiceFeign.getFriendRequestList());
        CompletableFuture<Void> voidCompletableFuture = Promise.PromiseAll(friendshipDtoFuture, friendRequestFuture, userCompletableFuture);
        voidCompletableFuture.join();

        try {
            // 获取它们的结果
            List<FriendshipDto> resFriendship = friendshipDtoFuture.get();
            List<FriendRequest> userListByIds = friendRequestFuture.get();
            User user = userCompletableFuture.get();
            System.out.println(user);
            System.out.println(resFriendship);
            System.out.println(userListByIds);
            // 在这里处理数据
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }



}
