package usyd.mingyi.springcloud.service;


import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.mongodb.entity.CloudMessage;

import java.util.List;
import java.util.Map;

public interface ChatService {
    CloudMessage retrieveDataFromMongoDB(String fromId, String toId);
    CloudMessage retrieveDataFromMongoDB(Long fromId, Long toId);
    List<CloudMessage> retrieveAllDataFromMongoDB(String userId);
    List<CloudMessage>  retrievePartlyDataFromMongoDB(String userId, Map<String,Long> localStorage);

    void saveMsgInCloud(ChatMessage chatMessage);
}
