package usyd.mingyi.sprincloud.mongodb.service;

import com.mongodb.client.result.UpdateResult;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.pojo.UserInfo;
import usyd.mingyi.sprincloud.entity.CommentFreeze;
import usyd.mingyi.sprincloud.mapper.CommentFreezeMapper;
import usyd.mingyi.sprincloud.mongodb.repository.UserInfoRepository;


@Service
@Slf4j
public class UserInfoService implements TCCService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private CommentFreezeMapper commentFreezeMapper;
    @Autowired
    private MongoService mongoService;

    @Transactional
    public void addCommentToLovedComments(Long userId, Long commentId) {

        String xid = RootContext.getXID();
        CommentFreeze oldFreeze = commentFreezeMapper.selectById(xid);
        if(oldFreeze!=null){
            return;
        }

        // 创建查询条件，userId匹配的文档

        mongoService.insertComment(userId,commentId);

        CommentFreeze commentFreeze = new CommentFreeze();
        commentFreeze.setState(CommentFreeze.TRY);
        commentFreeze.setUserId(userId);
        commentFreeze.setCommentId(commentId);
        commentFreeze.setXid(RootContext.getXID());
        commentFreezeMapper.insert(commentFreeze);
    }

    @Transactional
    public void removeCommentFromLovedComments(Long userId, Long commentId) {
        String xid = RootContext.getXID();
        CommentFreeze oldFreeze = commentFreezeMapper.selectById(xid);
        if(oldFreeze!=null){
            return;
        }

        // 创建查询条件，userId匹配的文档

        mongoService.removeComment(userId,commentId);

        CommentFreeze commentFreeze = new CommentFreeze();
        commentFreeze.setState(CommentFreeze.TRY);
        commentFreeze.setUserId(userId);
        commentFreeze.setCommentId(commentId);
        commentFreeze.setXid(RootContext.getXID());
        commentFreezeMapper.insert(commentFreeze);
    }


    @Override
    public boolean confirmAdd(BusinessActionContext context) {
        log.info("add成功");
        String xid = context.getXid();
        return commentFreezeMapper.deleteById(xid) == 1;

    }

    @Override
    public boolean cancelAdd(BusinessActionContext context) {
        log.info("add回滚");

        String xid = context.getXid();
        Long userId = (Long) context.getActionContext("userId");
        Long commentId = (Long) context.getActionContext("commentId");

        CommentFreeze commentFreeze = commentFreezeMapper.selectById(xid);
        if (commentFreeze==null){
              //空回滚
            commentFreeze = new CommentFreeze();
            commentFreeze.setUserId(userId);
            commentFreeze.setCommentId(commentId);
            commentFreeze.setXid(xid);
            commentFreeze.setState(CommentFreeze.CANCEL);
            commentFreezeMapper.insert(commentFreeze);
            return true;
        }
        if (commentFreeze.getState().equals(CommentFreeze.CANCEL)){
            return true;
        }

        mongoService.removeComment(commentFreeze.getUserId(),commentFreeze.getCommentId());
        commentFreeze.setState(CommentFreeze.CANCEL);
        return commentFreezeMapper.updateById(commentFreeze)==1;

    }



    @Override
    public boolean confirmRemove(BusinessActionContext context) {
        log.info("remove成功");
        String xid = context.getXid();
        return commentFreezeMapper.deleteById(xid) == 1;
    }

    @Override
    public boolean cancelRemove(BusinessActionContext context) {
        log.info("remove回滚");

        String xid = context.getXid();
        Long userId = (Long) context.getActionContext("userId");
        Long commentId = (Long) context.getActionContext("commentId");

        CommentFreeze commentFreeze = commentFreezeMapper.selectById(xid);
        if (commentFreeze==null){
            //空回滚
            commentFreeze = new CommentFreeze();
            commentFreeze.setUserId(userId);
            commentFreeze.setCommentId(commentId);
            commentFreeze.setXid(xid);
            commentFreeze.setState(CommentFreeze.CANCEL);
            commentFreezeMapper.insert(commentFreeze);
            return true;
        }
        if (commentFreeze.getState().equals(CommentFreeze.CANCEL)){
            return true;
        }

        mongoService.insertComment(commentFreeze.getUserId(),commentFreeze.getCommentId());
        commentFreeze.setState(CommentFreeze.CANCEL);
        return commentFreezeMapper.updateById(commentFreeze)==1;

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
