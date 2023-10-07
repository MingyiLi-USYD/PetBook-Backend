package usyd.mingyi.springcloud.mapstruct;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import usyd.mingyi.common.dto.*;
import usyd.mingyi.common.pojo.*;

import java.util.List;

@Mapper(componentModel = "spring")
//把这个包放在usyd.mingyi.springcloud包下 因为这样我其他的工程 通常在usyd.mingyi.springcloud包下 就自动把这个包扫进去了
//不需要再显示地在其他工程里面写一遍扫描路径
public interface PoConvertToDto {
    //项目关系比较简单 所以说Dto是继承的Po  所以说不需要加映射条件
    //因为是继承关系
    PostDto postToPostDto(Post post);

    List<PostDto> postToPostDto(List<Post> post);

    Page<PostDto> convertPostPage(Page<Post> postPage);

    FriendshipDto friendshipToFriendshipDto(Friendship friendship);

    FriendRequestDto friendRequestToFriendRequestDto(FriendRequest friendRequest);



    PetDto petToPetDto(Pet pet);

    CommentDto commentToCommentDto(Comment comment);
    List<CommentDto> commentsToCommentDtos(List<Comment> comment);

    Page<CommentDto> convertCommentPage(Page<Comment> commentPage);

    SubcommentDto subcommentToSubcommentDto(Subcomment subcomment);

    List<SubcommentDto> subcommentsToSubcommentDtos(List<Subcomment> subcomments);


    //LovePost 与 LovePostDto转化
    LovePostDto lovePostToLovePostDto(LovePost lovePost);

    List<LovePostDto> lovePostsToLovePostDtos(List<LovePost> lovePosts);

    Page<LovePostDto> convertLovePostPage(Page<LovePost> lovePostPage);

    //Mention 与 MentionDto转化

    MentionDto mentionToMentionDto(Mention mention);


    List<MentionDto> mentionsToMentionDtos(List<Mention> mentions);

    Page<MentionDto> convertMentionPage(Page<Mention> mentionPage);





}
