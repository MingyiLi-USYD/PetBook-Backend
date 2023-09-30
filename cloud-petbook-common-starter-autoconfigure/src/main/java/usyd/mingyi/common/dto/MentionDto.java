package usyd.mingyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.common.pojo.Mention;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.pojo.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentionDto extends Mention {
    private Post relevantPost;
    private User userInfo;
}
