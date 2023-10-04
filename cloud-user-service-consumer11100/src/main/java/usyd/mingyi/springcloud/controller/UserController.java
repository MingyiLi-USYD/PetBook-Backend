package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import feign.Param;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.common.UnauthorizedException;
import usyd.mingyi.common.dto.FriendRequestDto;
import usyd.mingyi.common.dto.FriendshipDto;
import usyd.mingyi.common.dto.UserDto;
import usyd.mingyi.common.feign.*;
import usyd.mingyi.common.pojo.Pet;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.pojo.UserInfo;
import usyd.mingyi.common.service.FeignInterceptor;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.common.utils.Promise;
import usyd.mingyi.common.utils.UserUtils;
import usyd.mingyi.springcloud.common.FriendRequestHandler;
import usyd.mingyi.springcloud.common.FriendshipHandler;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    UserBaseServiceFeign userBaseServiceFeign;
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
        userDto.setLoveIdList(lovedPostIdsPromise.get().stream().map(String::valueOf).collect(Collectors.toList()));
        userDto.setLovesReceived(loveReceivedCountPromise.get());
        userDto.setMentionsReceived(mentionReceivedCountPromise.get());
        userDto.setSubscribeIdList(subscribesPromise.get());
        userDto.setSubscriberIdList(subscribersPromise.get());
        userDto.setCommentsReceived(commentReceivedCountPromise.get());
        return R.success(userDto);
    }


    @GetMapping("/user/profile/{userId}")
    public R<UserDto> userProfile(@PathVariable("userId") Long userId) throws ExecutionException, InterruptedException {

        Promise<User> userPromise = Promise.buildPromise(() -> userServiceFeign.getUserById(userId));


        Promise<List<Post>> postPromise = Promise.buildPromise(() -> postServiceFeign.getPostsByUserId(userId));

        Promise<List<Pet>> petPromise = Promise.buildPromise(() -> petServiceFeign.getPetListByUserId(userId));

        Promise<List<Long>> lovedPostIdsPromise = Promise.buildPromise(interactionServiceFeign::getAllLovedPostsId);

        Promise<List<String>> subscribesPromise =
                Promise.buildPromise(interactionServiceFeign::getAllSubscribesInIds);

        Promise<List<String>> subscribersPromise =
                Promise.buildPromise(interactionServiceFeign::getAllSubscribersInIds);

        Promise.awaitAll(
                userPromise, petPromise, postPromise,
                lovedPostIdsPromise, subscribesPromise, subscribersPromise
        );


        UserDto userDto = new UserDto();
        User currentUser = userPromise.get();
        BeanUtils.copyProperties(currentUser, userDto);
        userDto.setPostList(postPromise.get());
        userDto.setPetList(petPromise.get());
        userDto.setLoveIdList(lovedPostIdsPromise.get().stream().map(String::valueOf).collect(Collectors.toList()));
        userDto.setSubscribeIdList(subscribesPromise.get());
        userDto.setSubscriberIdList(subscribersPromise.get());
        return R.success(userDto);
    }

    @GetMapping("/user/currentUser")
    public R<User> getCurrentUser() {
        User user = userServiceFeign.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("Login first");
        }
        return R.success(user);
    }

    @GetMapping("/users")
    public R<Page<User>> getAllUser(@RequestParam("current") Long current
            , @RequestParam("size") Long size, @RequestParam("keywords") String keywords) {

        return R.success(userServiceFeign.getAllUser(current, size, keywords));
    }

    @GetMapping("/user/userInfo")
    public R<UserInfo> getUserInfo() {
        UserInfo userInfo = userBaseServiceFeign.getUserInfo();
        return R.success(userInfo);
    }


    @PostMapping("/user/changeUser/{userId}")
    public R<String> changeUser(@PathVariable("userId")Long userId,
                                @RequestParam("role") String role,
                                @RequestParam("status")byte status) {
        User currentUser = userServiceFeign.getCurrentUser();
        User userById = userServiceFeign.getUserById(userId);
        if (currentUser.getStatus().equals((byte)1)
          && UserUtils.canModify(currentUser,userById,role)
        && UserUtils.isRightStatus(status)) {
            userById.setRole(role);
            userById.setStatus(status);
            userServiceFeign.changeUserRoleAndStatus(userById);
            return R.success("success");
        }

       return R.error("No right");
    }



}
