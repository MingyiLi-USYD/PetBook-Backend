package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import usyd.mingyi.common.common.R;
import usyd.mingyi.common.entity.ChatMessage;
import usyd.mingyi.common.entity.ServiceMessage;
import usyd.mingyi.common.entity.SystemMessage;
import usyd.mingyi.springcloud.service.SocketService;

@Slf4j
@RestController
public class SocketController {
    @Autowired
    SocketService socketService;

    @PostMapping("/sendChatMessage")
    R<String> sendChatMessage(@RequestBody ChatMessage chatMessage){
        socketService.asyncChatMessageToClient(chatMessage);
        return R.success("成功");
    }
    @PostMapping("/sendServiceMessage")
    R<String> sendServiceMessage(@RequestBody ServiceMessage serviceMessage){
        socketService.asyncServiceMessageToClient(serviceMessage);
        return R.success("成功");
    }
    @PostMapping("/sendSystemMessage")
    R<String> sendSystemMessage(@RequestBody SystemMessage systemMessage){
        //socketService.asyncChatMessageToClient(chatMessage);
        return R.success("成功");
    }


}
