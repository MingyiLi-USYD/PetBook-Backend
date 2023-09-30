package usyd.mingyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.common.pojo.Comment;
import usyd.mingyi.common.pojo.Post;
import usyd.mingyi.common.pojo.User;


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
