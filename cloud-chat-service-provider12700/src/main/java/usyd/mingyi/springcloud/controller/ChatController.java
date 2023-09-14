package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.service.ChatService;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.BaseContext;


@RestController
@Slf4j
public class ChatController {
     ChatService chatService;


    @PostMapping("/chat/message")
    public R<String> pushToUser(@RequestBody ChatMessage message) {
        Long currentId = BaseContext.getCurrentId();
        if (!message.getFromId().equals(String.valueOf(currentId))) {
            throw new CustomException("no right");
        }

        //chatService.sendMsgToQueue(message);
        //chatService.saveMsgInCloud(message);
        return R.success("成功发送");
    }

/*    @GetMapping("/chat/retrieve/{id}")
    public R<CloudMessage> getMessages(@PathVariable("id") Long toId){
        Long currentId = BaseContext.getCurrentId();
       return R.success(chatService.retrieveDataFromMongoDB(currentId,toId)) ;

    }

    @GetMapping("/chat/retrieve/all")
    public R<List<CloudMessage>> getAllMessages(){
        Long currentId = BaseContext.getCurrentId();
        return  R.success(chatService.retrieveAllDataFromMongoDB(String.valueOf(currentId)));
    }
    @PostMapping("/chat/retrieve/partly")
    public R<List<CloudMessage>> getPartly(@RequestBody Map<String,Long> localStorage){
        Long currentId = BaseContext.getCurrentId();
       if(!localStorage.isEmpty()){
          return R.success(chatService.retrievePartlyDataFromMongoDB(String.valueOf(BaseContext.getCurrentId()),localStorage));
       }
        return null;
    }*/

}
