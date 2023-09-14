package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.common.SubcommentHandler;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.SubcommentDto;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.CommentServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;
import usyd.mingyi.springcloud.utils.FieldUtils;

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
    public R<SubcommentDto> addSubcomment( @RequestBody Subcomment subcomment){
        Subcomment res = commentServiceFeign.addSubcomment(subcomment);
        User currentUser = userServiceFeign.getCurrentUser();
        SubcommentDto subcommentDto = poConvertToDto.subcommentToSubcommentDto(res);
        subcommentDto.setSubcommentUser(currentUser);
        return R.success(subcommentDto);
    }

    @GetMapping("/subcomments/{commentId}")
    public R<List<SubcommentDto>> getSubcommentsByCommentId(@PathVariable("commentId") Long commentId){
        List<Subcomment> subcommentList = commentServiceFeign.getSubcommentsByCommentId(commentId);
        List<Long> userIds = FieldUtils.extractField(subcommentList, Subcomment::getTargetUserId);
        List<User> userListByIds = userServiceFeign.getUserListByIds(userIds);
        List<SubcommentDto> subcommentDtoList = SubcommentHandler.handleUserInfo(subcommentList, userListByIds);

        return R.success(subcommentDtoList);
    }


}
