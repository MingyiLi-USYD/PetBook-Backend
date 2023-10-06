package usyd.mingyi.springcloud.service;

import usyd.mingyi.common.entity.ChatMessage;
import usyd.mingyi.common.pojo.CloudMessage;


import java.util.List;
import java.util.Map;

public interface ChatService {
    CloudMessage retrieveDataFromMongoDB(String fromId, String toId);
    CloudMessage retrieveDataFromMongoDB(Long fromId, Long toId);
    Map<String,CloudMessage> retrieveAllDataFromMongoDB(String userId);
    Map<String,CloudMessage> retrievePartlyDataFromMongoDB(String userId, Map<String,Long> localStorage);
    Map<String,CloudMessage> retrieveUnreadDataFromMongoDB(String userId, List<String> friendIds);
    void readMessage(String userId);

    void saveMsgInCloud(ChatMessage chatMessage);
}
