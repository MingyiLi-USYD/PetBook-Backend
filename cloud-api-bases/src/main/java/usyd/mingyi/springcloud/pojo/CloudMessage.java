package usyd.mingyi.springcloud.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.entity.ChatMessage;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CloudMessage {
    private String id;
    private Long latestTime;
    private String latestMsg;
    private Map<String,Long> participates;
    private List<ChatMessage> chatList;
    private User chatUser;
    private Long unRead;
}
