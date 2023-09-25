package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.LovePost;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LovePostDto extends LovePost {
    private User userInfo;
    private Post relevantPost;
}
