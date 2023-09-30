package usyd.mingyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.common.pojo.LovePost;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.pojo.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LovePostDto extends LovePost {
    private User userInfo;
    private Post relevantPost;
}
