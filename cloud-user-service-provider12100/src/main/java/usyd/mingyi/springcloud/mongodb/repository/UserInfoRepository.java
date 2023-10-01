package usyd.mingyi.springcloud.mongodb.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import usyd.mingyi.common.pojo.UserInfo;

public interface UserInfoRepository extends MongoRepository<UserInfo, Long> {

    UserInfo findByUserId(Long userId);

}
