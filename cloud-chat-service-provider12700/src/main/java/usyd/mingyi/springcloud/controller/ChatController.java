package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.entity.ChatMessage;
import usyd.mingyi.common.pojo.CloudMessage;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.service.ChatService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class ChatController {

    @Autowired
    ChatService chatService;


    @PostMapping("/chat/message")
    public R<String> saveChatMessage(@RequestBody ChatMessage message) {
        Long currentId = BaseContext.getCurrentId();
        if (!message.getFromId().equals(String.valueOf(currentId))) {
            throw new CustomException("no right");
        }
        chatService.saveMsgInCloud(message);
        return R.success("成功发送");
    }

    @GetMapping("/chat/retrieve/{id}")
    public R<CloudMessage> getMessages(@PathVariable("id") Long toId) {
        Long currentId = BaseContext.getCurrentId();
        return R.success(chatService.retrieveDataFromMongoDB(currentId, toId));

    }

    @GetMapping("/chat/retrieve/all")
    public R<Map<String, CloudMessage>> getAllMessages() {
        Long currentId = BaseContext.getCurrentId();
        return R.success(chatService.retrieveAllDataFromMongoDB(String.valueOf(currentId)));
    }

    @PostMapping("/chat/retrieve/partly")
    public R<Map<String, CloudMessage>> getPartly(@RequestBody Map<String, Long> localStorage) {

        if (!localStorage.isEmpty()) {
            return R.success(chatService.retrievePartlyDataFromMongoDB(String.valueOf(BaseContext.getCurrentId()), localStorage));
        }
        return R.success(new HashMap<>());
    }

    @PostMapping("/chat/retrieve/unread")
    public R<Map<String, CloudMessage>> getUnread(@RequestBody List<String> ids) {

        if (!ids.isEmpty()) {
            return R.success(chatService.retrieveUnreadDataFromMongoDB(String.valueOf(BaseContext.getCurrentId()), ids));
        }
        return R.success(new HashMap<>());
    }

}
