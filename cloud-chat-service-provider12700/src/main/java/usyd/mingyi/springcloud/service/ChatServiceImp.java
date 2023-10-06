package usyd.mingyi.springcloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usyd.mingyi.common.entity.ChatMessage;

import usyd.mingyi.common.pojo.CloudMessage;
import usyd.mingyi.common.utils.BaseContext;
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
    public Map<String, CloudMessage> retrieveAllDataFromMongoDB(String userId) {
        return cloudMessageService.getChatRecords(userId);
    }

    @Override
    public Map<String,CloudMessage> retrievePartlyDataFromMongoDB(String userId,  Map<String,Long> localStorage) {
        return cloudMessageService.getChatRecordsPartly(userId,localStorage);
    }

    @Override
    public Map<String,CloudMessage> retrieveUnreadDataFromMongoDB(String userId, List<String> friendIds) {
        return cloudMessageService.getChatRecordsUnread(userId,friendIds);
    }

    @Override
    public void readMessage(String targetId) {
        cloudMessageService.read(String.valueOf(BaseContext.getCurrentId()),targetId);
    }


    @Override
    public void saveMsgInCloud(ChatMessage chatMessage) {
        cloudMessageService.insertMsg(chatMessage);
    }


}
