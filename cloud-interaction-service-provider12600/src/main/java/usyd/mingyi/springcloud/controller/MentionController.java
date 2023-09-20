package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Mention;
import usyd.mingyi.springcloud.service.MentionService;
import usyd.mingyi.springcloud.utils.BaseContext;

@RestController
@Slf4j
public class MentionController {
    @Autowired
    MentionService mentionService;

    @PostMapping("/mention")
    public R<String> addMention(@RequestBody Mention mention) {
         mentionService.save(mention);
        return R.success("保存成功");
    }

    @GetMapping("/mentions")
    public R<Page<Mention>> getAllMentionedPosts(@RequestParam("current") Long current,
                                                 @RequestParam("pageSize") Integer pageSize) {
        Page<Mention> allMentionList = mentionService
                .getAllMentionList(BaseContext.getCurrentId(), current, pageSize);

        return R.success(allMentionList);
    }

    @GetMapping("/mention/read/{mentionId}")
    public R<String> markMentionAsRead(@PathVariable("mentionId") Long mentionId) {
        mentionService.markMentionAsRead(BaseContext.getCurrentId(),mentionId);
        return R.success("Success");
    }
    @GetMapping("/mentions/received/count")
    public R<Long> count() {
        return R.success(mentionService.
                countMentionsReceived(BaseContext.getCurrentId())
        );
    }


}
