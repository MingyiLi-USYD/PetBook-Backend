package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.MentionDto;
import usyd.mingyi.springcloud.pojo.Mention;
import usyd.mingyi.springcloud.service.InteractionServiceFeign;
import usyd.mingyi.springcloud.service.PostServiceFeign;
import usyd.mingyi.springcloud.service.UserServiceFeign;

@Slf4j
@RestController
public class MentionController {
    @Autowired
    InteractionServiceFeign interactionServiceFeign;
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
            mentionDto.setRelevantPost(postServiceFeign.getPost(mentionDto.getPostId()));
        });
        return R.success(mentionDtoPage);
    }

    @GetMapping("/mention/read/{mentionId}")
    public R<String> markMentionAsRead(@PathVariable("mentionId") Long mentionId) {
        return R.success(interactionServiceFeign.markMentionAsRead(mentionId));
    }


}
