package usyd.mingyi.springcloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.mongodb.entity.CloudMessage;
import usyd.mingyi.springcloud.mongodb.service.CloudMessageService;
import usyd.mingyi.springcloud.utils.CommonUtils;

import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class ChatServiceImp implements ChatService {

    @Autowired
    private CloudMessageService cloudMessageService;


    // 生成聊天ID
    @Override
    public CloudMessage retrieveDataFromMongoDB(String fromId, String toId) {
        return cloudMessageService.getCloudMessageById(CommonUtils.combineId(fromId,toId));
    }

    @Override
    public CloudMessage retrieveDataFromMongoDB(Long fromId, Long toId) {
        return retrieveDataFromMongoDB(String.valueOf(fromId),String.valueOf(toId));
    }

    @Override
    public List<CloudMessage> retrieveAllDataFromMongoDB(String userId) {
        return cloudMessageService.getChatRecords(userId);
    }

    @Override
    public List<CloudMessage> retrievePartlyDataFromMongoDB(String userId,  Map<String,Long> localStorage) {
        return cloudMessageService.getChatRecordsPartly(userId,localStorage);
    }

    public void sendMsgToQueue(ChatMessage chatMessage){

    }

    @Override
    public void saveMsgInCloud(ChatMessage chatMessage) {
        cloudMessageService.insertMsg(chatMessage);
    }


}
