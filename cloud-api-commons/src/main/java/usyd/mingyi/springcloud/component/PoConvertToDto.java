package usyd.mingyi.springcloud.component;

import org.mapstruct.Mapper;
import usyd.mingyi.springcloud.dto.PetDto;
import usyd.mingyi.springcloud.dto.PostDto;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.pojo.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PoConvertToDto {
    //项目关系比较简单 所以说Dto是继承的Po  所以说不需要加映射条件

    //因为是继承关系
    PostDto postToPostDto(Post post);

    List<PostDto> postToPostDto(List<Post> post);

    PetDto petToPetDto(Pet pet);
}
