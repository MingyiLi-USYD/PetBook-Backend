package usyd.mingyi.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import usyd.mingyi.common.entity.ChatMessage;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
