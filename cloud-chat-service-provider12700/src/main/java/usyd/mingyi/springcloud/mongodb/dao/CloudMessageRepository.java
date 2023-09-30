package usyd.mingyi.springcloud.mongodb.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import usyd.mingyi.common.pojo.CloudMessage;


@Repository
public interface CloudMessageRepository extends MongoRepository<CloudMessage, String> {


}
