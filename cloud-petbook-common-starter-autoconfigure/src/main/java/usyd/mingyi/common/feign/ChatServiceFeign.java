package usyd.mingyi.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.entity.ChatMessage;
import usyd.mingyi.common.entity.ServiceMessage;
import usyd.mingyi.common.entity.SystemMessage;
import usyd.mingyi.common.pojo.CloudMessage;
import java.util.List;
import java.util.Map;

@FeignClient(value = "chat-service-provider",configuration = FeignConfig.class)
public interface ChatServiceFeign {
    //下面是推送消息到MQ中
    @PostMapping("/sendChatMessage")
    String sendChatMessage(@RequestBody ChatMessage chatMessage);

    @PostMapping("/sendServiceMessage")
    String sendServiceMessage(@RequestBody ServiceMessage serviceMessage);

    @PostMapping("/sendSystemMessage")
    String sendSystemMessage(@RequestBody SystemMessage systemMessage);

    //下面是从MongoDb中保存或者获取消息


    @PostMapping("/chat/message")
    void saveChatMessage(@RequestBody ChatMessage message);

    @GetMapping("/chat/retrieve/{id}")
    CloudMessage getMessages(@PathVariable("id") Long toId);

    @GetMapping("/chat/retrieve/all")
    Map<String,CloudMessage> getAllMessages();

    @PostMapping("/chat/retrieve/partly")
    Map<String,CloudMessage> getPartly(@RequestBody Map<String, Long> localStorage);

    @PostMapping("/chat/retrieve/unread")
    Map<String,CloudMessage> getUnread(@RequestBody List<String> ids);

    @GetMapping("/chat/read/{userId}")
    String read(@PathVariable("userId")Long userId);
}
