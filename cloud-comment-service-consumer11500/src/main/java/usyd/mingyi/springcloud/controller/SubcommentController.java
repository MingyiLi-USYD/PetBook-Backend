package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.common.SubcommentHandler;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.SubcommentDto;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.CommentServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.FieldUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class SubcommentController {
    @Autowired
    CommentServiceFeign commentServiceFeign;
    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    PoConvertToDto poConvertToDto;
    @PostMapping("/subcomment")
    public R<SubcommentDto> addSubcomment( @RequestBody @Valid Subcomment subcomment){
        Comment commentByCommentId = commentServiceFeign.getCommentByCommentId(subcomment.getCommentId());
        if(commentByCommentId==null){
            throw new CustomException("不存在此comment");
        }
        Subcomment res = commentServiceFeign.addSubcomment(subcomment);
        User currentUser = userServiceFeign.getCurrentUser();
        SubcommentDto subcommentDto = poConvertToDto.subcommentToSubcommentDto(res);
        subcommentDto.setSubcommentUser(currentUser);
        return R.success(subcommentDto);
    }

    @GetMapping("/subcomments/{commentId}")
    public R<List<SubcommentDto>> getSubcommentsByCommentId(@PathVariable("commentId") Long commentId){
        List<Subcomment> subcomments = commentServiceFeign.getSubcommentsByCommentId(commentId);
        List<SubcommentDto> subcommentDtos = poConvertToDto.subcommentsToSubcommentDtos(subcomments);
        subcommentDtos.forEach(subcommentDto -> {
            subcommentDto.setSubcommentUser(userServiceFeign.getUserById(subcommentDto.getUserId()));
            if(subcommentDto.getIsReply()){
                subcommentDto.setRelpyUser(userServiceFeign.getUserById(subcommentDto.getReplyUserId()));
            }
        });
        return R.success(subcommentDtos);
    }


}
