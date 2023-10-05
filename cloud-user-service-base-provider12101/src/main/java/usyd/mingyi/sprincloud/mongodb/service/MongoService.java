package usyd.mingyi.sprincloud.mongodb.service;

import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.pojo.UserInfo;
import usyd.mingyi.sprincloud.mongodb.repository.UserInfoRepository;

import java.util.Set;


@Repository
@Slf4j
public class MongoService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager",propagation = Propagation.REQUIRES_NEW)
    public void insertComment(Long userId, Long commentId) {

        Query query = new Query(Criteria.where("userId").is(userId));


        // 创建更新操作，addToSet操作将commentId添加到lovedComments字段中，确保不重复
        Update update = new Update().addToSet("lovedComments", commentId);

        // 执行更新操作，如果找不到匹配条件的文档，就会创建一个新的文档
        UpdateResult updateResult = mongoTemplate.upsert(query, update, UserInfo.class);
        if (updateResult.getModifiedCount()==0||!updateResult.wasAcknowledged()) {

            throw new CustomException("Already loved");
        }

    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    public void removeComment(Long userId, Long commentId) {
        Query query = new Query(Criteria.where("userId").is(userId));

        // 创建更新操作，使用$pull操作符从lovedComments字段中删除指定的commentId
        Update update = new Update().pull("lovedComments", commentId);

        // 执行更新操作
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserInfo.class);

        if (updateResult.getModifiedCount()==0||!updateResult.wasAcknowledged()) {

            throw new CustomException("Never loved before");
        }

        // 删除成功

    }


}
