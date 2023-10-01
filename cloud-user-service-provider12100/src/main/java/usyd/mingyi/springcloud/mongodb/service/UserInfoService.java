package usyd.mingyi.springcloud.mongodb.service;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import usyd.mingyi.common.pojo.UserInfo;
import usyd.mingyi.springcloud.mongodb.repository.UserInfoRepository;

@Service
public class UserInfoService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserInfoRepository userInfoRepository;

    public boolean addCommentToLovedComments(Long userId, Long commentId) {
        // 创建查询条件，userId匹配的文档
        Query query = new Query(Criteria.where("userId").is(userId));


        // 创建更新操作，addToSet操作将commentId添加到lovedComments字段中，确保不重复
        Update update = new Update().addToSet("lovedComments", commentId);

        // 执行更新操作，如果找不到匹配条件的文档，就会创建一个新的文档
        UpdateResult updateResult = mongoTemplate.upsert(query, update, UserInfo.class);

        // 插入成功
        return !updateResult.wasAcknowledged() || updateResult.getModifiedCount() < 1;

    }


    public boolean removeCommentFromLovedComments(Long userId, Long commentId) {
        // 创建查询条件，userId匹配的文档
        Query query = new Query(Criteria.where("userId").is(userId));

        // 创建更新操作，使用$pull操作符从lovedComments字段中删除指定的commentId
        Update update = new Update().pull("lovedComments", commentId);

        // 执行更新操作
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserInfo.class);

        // 删除成功
        return !updateResult.wasAcknowledged() || updateResult.getModifiedCount() < 1;
    }


    public UserInfo getUserInfoByUserId(Long userId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);

        if (userInfo == null) {
            // 如果未找到匹配的记录，创建一个新的UserInfo对象并初始化字段为空数组
            userInfo = new UserInfo();
            userInfo.setUserId(userId);
        }
        return userInfo;
    }


    public boolean addSubCommentToLovedSubcomments(Long userId, Long subcommentId) {
        // 创建查询条件，userId匹配的文档
        Query query = new Query(Criteria.where("userId").is(userId));


        // 创建更新操作，addToSet操作将commentId添加到lovedComments字段中，确保不重复
        Update update = new Update().addToSet("lovedSubcomments", subcommentId);

        // 执行更新操作，如果找不到匹配条件的文档，就会创建一个新的文档
        UpdateResult updateResult = mongoTemplate.upsert(query, update, UserInfo.class);

        // 插入成功
        return !updateResult.wasAcknowledged() || updateResult.getModifiedCount() < 1;

    }


    public boolean removeSubcommentFromLovedSubcomments(Long userId, Long subcommentId) {
        // 创建查询条件，userId匹配的文档
        Query query = new Query(Criteria.where("userId").is(userId));

        // 创建更新操作，使用$pull操作符从lovedComments字段中删除指定的commentId
        Update update = new Update().pull("lovedSubcomments", subcommentId);

        // 执行更新操作
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserInfo.class);

        // 删除成功
        return !updateResult.wasAcknowledged() || updateResult.getModifiedCount() < 1;
    }



}
