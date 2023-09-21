package usyd.mingyi.springcloud.component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import usyd.mingyi.springcloud.dto.*;
import usyd.mingyi.springcloud.pojo.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PoConvertToDto {
    //项目关系比较简单 所以说Dto是继承的Po  所以说不需要加映射条件

    //因为是继承关系
    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "love", source = "love")
    @Mapping(target = "postContent", source = "postContent")
    @Mapping(target = "postTag", source = "postTag")
    @Mapping(target = "postTitle", source = "postTitle")
    @Mapping(target = "postTime", source = "postTime")
    @Mapping(target = "publishTime", source = "publishTime")
    @Mapping(target = "visible", source = "visible")
    @Mapping(target = "viewCount", source = "viewCount")
    @Mapping(target = "coverImage", source = "coverImage")
    PostDto postToPostDto(Post post);

    FriendshipDto friendshipToFriendshipDto(Friendship friendship);

    FriendRequestDto friendRequestToFriendRequestDto(FriendRequest friendRequest);

    List<PostDto> postToPostDto(List<Post> post);

    PetDto petToPetDto(Pet pet);

    CommentDto commentToCommentDto(Comment comment);

    SubcommentDto subcommentToSubcommentDto(Subcomment subcomment);

    List<SubcommentDto> subcommentsToSubcommentDtos(List<Subcomment> subcomments);

    Page<CommentDto> convertPage(Page<Comment> commentPage);

    //LovePost 与 LovePostDto转化
    LovePostDto lovePostToLovePostDto(LovePost lovePost);

    List<LovePostDto> lovePostsToLovePostDtos(List<LovePost> lovePosts);

    Page<LovePostDto> convertLovePostPage(Page<LovePost> lovePostPage);

    //Mention 与 MentionDto转化

    MentionDto mentionToMentionDto(Mention mention);


    List<MentionDto> mentionsToMentionDtos(List<Mention> mentions);

    Page<MentionDto> convertMentionPage(Page<Mention> mentionPage);





}
