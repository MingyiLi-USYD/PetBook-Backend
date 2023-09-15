package usyd.mingyi.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import usyd.mingyi.springcloud.config.FeignConfig;
import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.entity.RequestMessage;
import usyd.mingyi.springcloud.entity.ServiceMessage;
import usyd.mingyi.springcloud.entity.SystemMessage;
import usyd.mingyi.springcloud.pojo.CloudMessage;

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
    List<CloudMessage> getAllMessages();

    @PostMapping("/chat/retrieve/partly")
    List<CloudMessage> getPartly(@RequestBody Map<String, Long> localStorage);
}
