package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.MentionDto;
import usyd.mingyi.springcloud.pojo.Comment;
import usyd.mingyi.springcloud.pojo.Mention;
import usyd.mingyi.springcloud.service.CommentServiceFeign;
import usyd.mingyi.springcloud.service.InteractionServiceFeign;
import usyd.mingyi.springcloud.service.PostServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;

import javax.validation.Valid;

@Slf4j
@RestController
public class MentionController {
    @Autowired
    InteractionServiceFeign interactionServiceFeign;
    @Autowired
    CommentServiceFeign commentServiceFeign;

    @Autowired
    PoConvertToDto poConvertToDto;
    @Autowired
    PostServiceFeign postServiceFeign;
    @Autowired
    UserServiceFeign userServiceFeign;


    @GetMapping("/mentions")
    public R<Page<MentionDto>> getAllMentionedPosts(@RequestParam("current") Long current,
                                                    @RequestParam("pageSize") Integer pageSize) {
        Page<Mention> mentionPage = interactionServiceFeign.getAllMentionedPosts(current, pageSize);
        Page<MentionDto> mentionDtoPage = poConvertToDto.convertMentionPage(mentionPage);
        mentionDtoPage.getRecords().forEach(mentionDto -> {
            mentionDto.setUserInfo(userServiceFeign.getUserById(mentionDto.getUserId()));
            mentionDto.setRelevantPost(postServiceFeign.getPostByPostId(mentionDto.getPostId()));
        });
        return R.success(mentionDtoPage);
    }

    @PostMapping("/mention/reply")
    public R<String> addCommentAndReadMention( @RequestBody @Valid Comment comment,
                                               @RequestParam("mentionId") Long mentionId) {
        commentServiceFeign.addComment(comment);
        interactionServiceFeign.markMentionAsRead(mentionId);
        return R.success("成功");
    }



    @GetMapping("/mention/read/{mentionId}")
    public R<String> markMentionAsRead(@PathVariable("mentionId") Long mentionId) {
        return R.success(interactionServiceFeign.markMentionAsRead(mentionId));
    }


}
