package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.Mention;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentionDto extends Mention {
    private Post relevantPost;
    private User userInfo;
}
