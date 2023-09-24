package usyd.mingyi.springcloud.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.pojo.User;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "chat")
public class CloudMessage {
    @Id
    private String id;
    private Long latestTime;
    private String latestMsg;
    private Map<String,Long> participates;
    private List<ChatMessage> chatList;
    private User chatUser;
    private Long unRead;
}
