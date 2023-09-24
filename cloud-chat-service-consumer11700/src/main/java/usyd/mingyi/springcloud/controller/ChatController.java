package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.pojo.CloudMessage;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.ChatService;
import usyd.mingyi.springcloud.service.ChatServiceFeign;
import usyd.mingyi.springcloud.service.FriendServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.BaseContext;
import usyd.mingyi.springcloud.utils.FieldUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class ChatController {

     @Autowired
     ChatServiceFeign chatServiceFeign;

     @Autowired
     UserServiceFeign userServiceFeign;

     @Autowired
     FriendServiceFeign friendServiceFeign;



    @PostMapping("/chat/message")
    public R<String> pushToUser(@RequestBody ChatMessage message) {
        Long currentId = BaseContext.getCurrentId();
        //还要检查是否是朋友

        if (!message.getFromId().equals(String.valueOf(currentId))) {
            throw new CustomException("no right");
        }
        chatServiceFeign.saveChatMessage(message);
        chatServiceFeign.sendChatMessage(message);
        return R.success("成功发送");
    }

    @GetMapping("/chat/retrieve/{id}")
    public R<CloudMessage> getMessages(@PathVariable("id") Long toId){
        return R.success(chatServiceFeign.getMessages(toId)) ;
    }

    @GetMapping("/chat/retrieve/all")
    public R<Map<String,CloudMessage>> getAllMessages(){
        Long currentId = BaseContext.getCurrentId();
        Map<String,CloudMessage> cloudMessages = chatServiceFeign.getAllMessages();

        for (Map.Entry<String, CloudMessage> entry : cloudMessages.entrySet()) {
            String friendId = entry.getKey();
            User friend = userServiceFeign.getUserById(Long.valueOf(friendId));
            entry.getValue().setChatUser(friend);
        }

        return  R.success(cloudMessages);
    }
    @PostMapping("/chat/retrieve/partly")
    public R<Map<String,CloudMessage>> getPartly(@RequestBody Map<String,Long> localStorage){
        Long currentId = BaseContext.getCurrentId();

        //先查本地有的
        Map<String,CloudMessage> cloudMessages = chatServiceFeign.getPartly(localStorage);

        //再查出所有好友
        //然后去重刚才已经查的
        List<Long> friendshipIdList = friendServiceFeign.getFriendshipIdList();
        Set<String> strings = localStorage.keySet();
        List<String> friendshipStringIdList = friendshipIdList
                .stream().map(String::valueOf).collect(Collectors.toList());
        friendshipStringIdList.removeAll(strings);
        //查好友中未读>0的
        Map<String,CloudMessage> unread = chatServiceFeign.getUnread(friendshipStringIdList);
        //合并
        Map<String, CloudMessage> mergedCloudMessages = mergeMapCloudMessage(unread, cloudMessages);
        //补齐好友的具体信息
        for (Map.Entry<String, CloudMessage> entry : mergedCloudMessages.entrySet()) {
            String friendId = entry.getKey();
            User friend = userServiceFeign.getUserById(Long.valueOf(friendId));
            entry.getValue().setChatUser(friend);
        }

        return R.success(mergedCloudMessages);

    }

    private Map<String,CloudMessage> mergeMapCloudMessage(Map<String,CloudMessage> source,Map<String,CloudMessage> target){
        for (Map.Entry<String, CloudMessage> entry : source.entrySet()) {
            String key = entry.getKey();
            if(!target.containsKey(key)){
                target.put(key,entry.getValue());
            }
        }
        return target;
    }


}
