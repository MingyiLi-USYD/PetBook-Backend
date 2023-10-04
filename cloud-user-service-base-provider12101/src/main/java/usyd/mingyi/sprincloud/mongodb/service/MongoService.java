package usyd.mingyi.sprincloud.mongodb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.pojo.UserInfo;
import usyd.mingyi.sprincloud.mongodb.repository.UserInfoRepository;


@Repository
@Slf4j
public class MongoService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager",propagation = Propagation.REQUIRES_NEW)
    public void insertComment(Long userId, Long commentId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);

        if (userInfo == null) {
            // 如果找不到匹配的 UserInfo，创建一个新的 UserInfo 对象
            userInfo = new UserInfo();
            userInfo.setUserId(userId);
        }

        // 将 commentId 添加到 lovedComments 中，确保不重复
        userInfo.getLovedComments().add(commentId);
        UserInfo save = userInfoRepository.save(userInfo);// 保存或更新 UserInfo 对象
        if (save == null) {
            throw new CustomException("保存失败");
        }
        log.info("成功");


    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    public void removeComment(Long userId, Long commentId) {
        UserInfo userInfo = userInfoRepository.findByUserId(userId);

        if (userInfo != null) {
            // 从 lovedComments 中移除 commentId
            userInfo.getLovedComments().remove(commentId);
            UserInfo save = userInfoRepository.save(userInfo); // 保存或更新 UserInfo 对象
            if (save == null) {
                throw new CustomException("保存失败");
            }
            log.info("成功移除");
        } else {
            log.info("UserInfo 不存在");
        }

    }


}
