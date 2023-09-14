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
import usyd.mingyi.springcloud.utils.BaseContext;


@RestController
@Slf4j
public class ChatController {
     @Autowired
     ChatService chatService;


    @PostMapping("/chat/message")
    public R<String> pushToUser(@RequestBody ChatMessage message) {
        Long currentId = BaseContext.getCurrentId();
        if (!message.getFromId().equals(String.valueOf(currentId))) {
            throw new CustomException("no right");
        }
        //chatService.saveMsgInCloud(message);
        //chatService.sendMsgToQueue(message);
        return R.success("成功发送");
    }


}
