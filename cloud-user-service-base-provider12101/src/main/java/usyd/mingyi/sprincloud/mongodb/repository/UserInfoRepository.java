package usyd.mingyi.sprincloud.mongodb.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import usyd.mingyi.common.pojo.UserInfo;

@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, Long> {

    UserInfo findByUserId(Long userId);

}
