package usyd.mingyi.springcloud.mongodb.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import usyd.mingyi.springcloud.mongodb.entity.CloudMessage;

import java.util.Optional;


@Repository
public interface CloudMessageRepository extends MongoRepository<CloudMessage,String> {


}
