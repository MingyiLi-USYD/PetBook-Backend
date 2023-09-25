package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto extends Comment {
    private User commentUser;
    private List<SubcommentDto> subcommentDtos;
    private Long subcommentsLength;
    private Post relevantPost;
}
