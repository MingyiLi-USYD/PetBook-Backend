package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.FriendRequestHandler;
import usyd.mingyi.springcloud.common.FriendshipHandler;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.dto.FriendRequestDto;
import usyd.mingyi.springcloud.dto.FriendshipDto;
import usyd.mingyi.springcloud.dto.UserDto;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.*;
import usyd.mingyi.springcloud.utils.BaseContext;
import usyd.mingyi.springcloud.utils.Promise;

import java.util.List;
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
    @Autowired
    PetServiceFeign petServiceFeign;
    @Autowired
    InteractionServiceFeign interactionServiceFeign;
    @Autowired
    CommentServiceFeign commentServiceFeign;
    @Autowired
    FriendshipHandler friendshipHandler;
    @Autowired
    FriendRequestHandler friendRequestHandler;


    @GetMapping("/user/{userId}")
    public R<User> getUser(@PathVariable("userId") Long userId) {

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

        Promise<List<Long>> lovedPostIdsPromise = Promise.buildPromise(interactionServiceFeign::getAllLovedPostsId);

        Promise<Long> loveReceivedCountPromise = Promise.buildPromise(interactionServiceFeign::countLovePostsReceived);

        Promise<Long> mentionReceivedCountPromise =
                Promise.buildPromise(interactionServiceFeign::countMentionsReceived);

        Promise<List<String>> subscribesPromise =
                Promise.buildPromise(interactionServiceFeign::getAllSubscribesInIds);

        Promise<List<String>> subscribersPromise =
                Promise.buildPromise(interactionServiceFeign::getAllSubscribersInIds);
        Promise<Long> commentReceivedCountPromise = Promise.buildPromise(commentServiceFeign::countCommentsReceived);

        Promise.awaitAll(
                userPromise, friendshipDtosPromise, friendRequestDtosPromise,
                petPromise, postPromise, lovedPostIdsPromise, loveReceivedCountPromise,
                mentionReceivedCountPromise, commentReceivedCountPromise
        );


        UserDto userDto = new UserDto();
        User currentUser = userPromise.get();
        BeanUtils.copyProperties(currentUser, userDto);
        userDto.setFriendshipDtoList(friendshipDtosPromise.get());
        userDto.setFriendRequestDtoList(friendRequestDtosPromise.get());
        userDto.setPostList(postPromise.get());
        userDto.setPetList(petPromise.get());
        userDto.setLoveIdList(lovedPostIdsPromise.get().stream().map(String::valueOf).toList());
        userDto.setLovesReceived(loveReceivedCountPromise.get());
        userDto.setMentionsReceived(mentionReceivedCountPromise.get());
        userDto.setSubscribeIdList(subscribesPromise.get());
        userDto.setSubscriberIdList(subscribersPromise.get());
        userDto.setCommentsReceived(commentReceivedCountPromise.get());
        return R.success(userDto);
    }

    @GetMapping("/currentUser")
    public R<User> getCurrentUser() {
        User user = userServiceFeign.getCurrentUser();
        return R.success(user);
    }

    @GetMapping("/users")
    public R<Page<User>> getAllUser(@RequestParam("current") Long current
            , @RequestParam("size") Long size, @RequestParam("keywords") String keywords) {
/*        userServiceFeign
        return R.success(page);*/
        return null;
    }


}
