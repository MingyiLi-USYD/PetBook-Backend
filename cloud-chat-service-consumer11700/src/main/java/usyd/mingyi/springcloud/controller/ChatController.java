package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.pojo.CloudMessage;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.ChatService;
import usyd.mingyi.springcloud.service.ChatServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class ChatController {

     @Autowired
     ChatServiceFeign chatServiceFeign;

     @Autowired
     UserServiceFeign userServiceFeign;



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
    public R<List<CloudMessage>> getAllMessages(){
        Long currentId = BaseContext.getCurrentId();
        List<CloudMessage> cloudMessages = chatServiceFeign.getAllMessages();
        cloudMessages.forEach(cloudMessage -> {
            List<String> participates = cloudMessage.getParticipates();
            for (int i = 0; i < participates.size(); i++) {
                if(participates.get(i)!=null&&!participates.get(i).equals(String.valueOf(currentId))){
                    User user = userServiceFeign.getUserById(Long.valueOf(participates.get(i)));
                    cloudMessage.setChatUser(user);
                    break;
                }
            }
        });
        return  R.success(cloudMessages);
    }
    @PostMapping("/chat/retrieve/partly")
    public R<List<CloudMessage>> getPartly(@RequestBody Map<String,Long> localStorage){
/*        Long currentId = BaseContext.getCurrentId();
        if(!localStorage.isEmpty()){
            return R.success(chatService.retrievePartlyDataFromMongoDB(String.valueOf(BaseContext.getCurrentId()),localStorage));
        }*/
        return null;
    }

}
