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
import usyd.mingyi.springcloud.utils.BaseContext;
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
    @Autowired
    FriendshipHandler friendshipHandler;
    @Autowired
    FriendRequestHandler friendRequestHandler;



    @GetMapping("/user/{userId}")
    public R<User> getUser(@PathVariable("userId") Long userId){

        User userById = userServiceFeign.getUserById(userId);
        return R.success(userById);
    }

    @GetMapping("/user/init")
    public R<UserDto> initUserInfo() throws ExecutionException, InterruptedException {
        Long currentId = BaseContext.getCurrentId();
        log.info(currentId.toString());

        Promise<User> userPromise = Promise.buildPromise(userServiceFeign::getCurrentUser);

        Promise<List<FriendshipDto>> friendshipDtosPromise =
                Promise.buildPromise(friendServiceFeign::getFriendshipList).then(friendshipHandler::convert);

        Promise<List<FriendRequestDto>> friendRequestDtosPromise
                = Promise.buildPromise(friendServiceFeign::getFriendRequestList).then(friendRequestHandler::convert);

        Promise<List<Post>> postPromise = Promise.buildPromise(postServiceFeign::getMyPosts);
        Promise<List<Pet>> petPromise = Promise.buildPromise(petServiceFeign::getPetList);

        Promise.awaitAll(userPromise,friendshipDtosPromise,friendRequestDtosPromise,petPromise,postPromise);



        UserDto userDto = new UserDto();
        User currentUser = userPromise.get();
        BeanUtils.copyProperties(currentUser,userDto);
        userDto.setFriendshipDtoList(friendshipDtosPromise.get());
        userDto.setFriendRequestDtoList(friendRequestDtosPromise.get());
        userDto.setPostList(postPromise.get());
        userDto.setPetList(petPromise.get());
        return R.success(userDto);
    }

    @GetMapping("/user/init2")
    public R<UserDto> promise()  {
        User currentUser = userServiceFeign.getCurrentUser();
        System.out.println(currentUser);
        return null;

    }



}
