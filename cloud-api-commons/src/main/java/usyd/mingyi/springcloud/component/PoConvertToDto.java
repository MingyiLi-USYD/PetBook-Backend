package usyd.mingyi.springcloud.component;

import org.mapstruct.Mapper;
import usyd.mingyi.springcloud.dto.*;
import usyd.mingyi.springcloud.pojo.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PoConvertToDto {
    //项目关系比较简单 所以说Dto是继承的Po  所以说不需要加映射条件

    //因为是继承关系
    PostDto postToPostDto(Post post);

    FriendshipDto friendshipToFriendshipDto(Friendship friendship);
    FriendRequestDto friendRequestToFriendRequestDto(FriendRequest friendRequest);

    List<PostDto> postToPostDto(List<Post> post);

    PetDto petToPetDto(Pet pet);

    CommentDto commentToCommentDto(Comment comment);




}
