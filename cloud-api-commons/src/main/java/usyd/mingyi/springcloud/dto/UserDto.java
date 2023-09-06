package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.pojo.Post;
import usyd.mingyi.springcloud.pojo.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends User {
    private List<Post> postList;
    private List<Pet> petList;
    private List<Post> loveList;
    private List<FriendshipDto> friendshipDtoList;
    private List<FriendRequestDto> friendRequestDtoList;
    private List<String> subscribeIdList;
    private List<String> subscriberIdList;
    private List<String> loveIdList;
    private Integer MentionsReceived;
    private Integer CommentsReceived;
    private Integer LovesReceived;

}
