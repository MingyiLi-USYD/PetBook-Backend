package usyd.mingyi.springcloud.mongodb.service;

import org.bson.Document;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.entity.ChatMessage;
import usyd.mingyi.common.pojo.CloudMessage;
import usyd.mingyi.springcloud.mongodb.dao.CloudMessageRepository;
import usyd.mingyi.springcloud.utils.CommonUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class CloudMessageService {
    @Autowired
    CloudMessageRepository cloudMessageRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    public void insertMsg(ChatMessage chatMessage) {
        String combinedId = CommonUtils.combineId(chatMessage.getFromId(), chatMessage.getToId());
        if (existsCloudMessageById(combinedId)) {
            Query query = Query.query(Criteria.where("id").is(combinedId));
            Update update = new Update()
                    .set("latestTime", chatMessage.getDate())
                    .set("latestMsg", chatMessage.getContent())
                    .push("chatList", chatMessage).inc("participates." + chatMessage.getToId(), 1L);
            mongoTemplate.updateFirst(query, update, CloudMessage.class);
        } else {
            List<ChatMessage> chatList = new ArrayList<>(1);
            chatList.add(chatMessage);
            CloudMessage newCloudMessage = new CloudMessage();
            newCloudMessage.setId(combinedId);
            newCloudMessage.setLatestTime(System.currentTimeMillis());
            newCloudMessage.setChatList(chatList);
            newCloudMessage.setLatestMsg(chatMessage.getContent());
            Map<String, Long> participates = new HashMap<>();
            participates.put(chatMessage.getFromId(), 0L);
            participates.put(chatMessage.getToId(), 1L);
            newCloudMessage.setUnRead(0L);
            newCloudMessage.setParticipates(participates);
            cloudMessageRepository.insert(newCloudMessage);
        }
    }

    public CloudMessage getCloudMessageById(String id) {

        Optional<CloudMessage> optionalCloudMessage = cloudMessageRepository.findById(id);
        if (optionalCloudMessage.isEmpty()) {
            throw new CustomException("No chat records found");
        }
        return optionalCloudMessage.orElse(new CloudMessage());
    }

    public boolean existsCloudMessageById(String id) {
        Query query = Query.query(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, CloudMessage.class);
    }

    public Map<String, CloudMessage> getChatRecords(String userId) {
        AggregationOperation matchOperation = Aggregation.match(Criteria.where("id").regex(userId));
        AggregationOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "latestTime");
        AggregationOperation limitOperation = Aggregation.limit(10);
        AggregationOperation projectOperation = Aggregation.project(CloudMessage.class).and("chatList").slice(-100);
        TypedAggregation<CloudMessage> aggregation = Aggregation.newAggregation(CloudMessage.class, matchOperation, sortOperation, limitOperation, projectOperation);
        List<CloudMessage> cloudMessages = mongoTemplate.aggregate(aggregation, CloudMessage.class).getMappedResults();
        return listCloudMessage2MapCloudMessage(userId, cloudMessages);
    }


    public Map<String, CloudMessage> getChatRecordsPartly(String userId, Map<String, Long> localStorage) {
        //效率很低 由于不是很熟悉mongodb 就先这样写着 后期再改
        //根据用户本地已有的数据返回
        List<CloudMessage> res = new ArrayList<>();
        for (String friendId : localStorage.keySet()) {
            Long lastTime = localStorage.get(friendId);
            AggregationOperation filterOperation = Aggregation.match(Criteria.where("chatList.date").gt(lastTime));
            CloudMessage oneChatRecord = this.getOneChatRecord(userId, friendId, filterOperation);

            if (oneChatRecord != null) {
                oneChatRecord.setChatList(
                        oneChatRecord.getChatList().stream()
                                .filter(chatMessage -> chatMessage.getDate()>lastTime)
                                .collect(Collectors.toList()));

                res.add(oneChatRecord);
            }
        }

        return listCloudMessage2MapCloudMessage(userId, res);
    }

    public Map<String, CloudMessage> getChatRecordsUnread(String userId, List<String> friendIds) {
        List<CloudMessage> res = new ArrayList<>();
        for (String friendId : friendIds) {
            CloudMessage oneChatRecord = this.getOneChatRecord(userId, friendId);
            if (oneChatRecord != null) {

                res.add(oneChatRecord);
            }
        }
        return listCloudMessage2MapCloudMessage(userId, res);
    }

    public CloudMessage getOneChatRecord(String userId, String friendId, List<AggregationOperation> operations) {
        TypedAggregation<CloudMessage> aggregation = cloudMessageQuery(userId, friendId, operations);
        CloudMessage cloudMessages = mongoTemplate.aggregate(aggregation, CloudMessage.class).getUniqueMappedResult();
        return cloudMessages;
    }

    public CloudMessage getOneChatRecord(String userId, String friendId) {
        return getOneChatRecord(userId, friendId, new ArrayList<>());
    }

    public CloudMessage getOneChatRecord(String userId, String friendId, AggregationOperation... operation) {
        return getOneChatRecord(userId, friendId, Arrays.stream(operation).collect(Collectors.toList()));
    }

    private TypedAggregation<CloudMessage> cloudMessageQuery(String userId, String friendId, List<AggregationOperation> operations) {
        String recordId = CommonUtils.combineId(userId, friendId);
        AggregationOperation matchOperation = Aggregation.match(Criteria.where("id").is(recordId).and("participates." + userId).gt(0L));
        AggregationOperation projectOperation = Aggregation.project(CloudMessage.class).and("chatList").slice(-100);
        operations.add(matchOperation);
        operations.add(projectOperation);
        return Aggregation.newAggregation(CloudMessage.class, operations);
    }

    private Map<String, CloudMessage> listCloudMessage2MapCloudMessage(String myId, List<CloudMessage> cloudMessages) {
        HashMap<String, CloudMessage> mapCloudMessage = new HashMap<>();
        for (CloudMessage cloudMessage : cloudMessages) {
            Map<String, Long> participates = cloudMessage.getParticipates();
            Set<String> userIds = participates.keySet();
            for (String userId : userIds) {
                if (!userId.equals(myId)) {
                    if (participates.containsKey(myId)) {
                        cloudMessage.setUnRead(participates.get(myId));
                    }
                    mapCloudMessage.put(userId, cloudMessage);

                }
            }
        }
        return mapCloudMessage;
    }


    public void read(String userId,String targetId){

        String combinedId = CommonUtils.combineId(userId, targetId);
        if (existsCloudMessageById(combinedId)) {
            Query query = Query.query(Criteria.where("id").is(combinedId));
            Update update = new Update()
                .set("participates." + userId, 0L);
            mongoTemplate.updateFirst(query, update, CloudMessage.class);
        }
    }

}
