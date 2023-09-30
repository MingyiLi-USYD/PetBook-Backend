package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.dto.SubcommentDto;
import usyd.mingyi.common.feign.CommentServiceFeign;
import usyd.mingyi.common.feign.UserServiceFeign;
import usyd.mingyi.common.pojo.Comment;
import usyd.mingyi.common.pojo.Subcomment;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.springcloud.common.SubcommentHandler;
import usyd.mingyi.springcloud.mapstruct.PoConvertToDto;


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
    public R<SubcommentDto> addSubcomment(@RequestBody @Valid Subcomment subcomment){
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
