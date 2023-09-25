package usyd.mingyi.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends RequestMessage{
    private String content;

    public ChatMessage(String fromId, Long date, String toId, String content) {
        super(fromId, date, toId);
        this.content = content;
    }
}
