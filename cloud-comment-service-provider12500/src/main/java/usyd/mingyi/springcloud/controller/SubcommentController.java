package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.service.SubcommentService;
import usyd.mingyi.springcloud.utils.BaseContext;

@RestController
@Slf4j
public class SubcommentController {
    @Autowired
    SubcommentService subcommentService;

    @PostMapping("/subcomment")
    @ResponseBody
    public R<Subcomment> addSubcomment(@RequestBody Subcomment subcomment){
        Long id = BaseContext.getCurrentId();
        subcomment.setUserId(id);
        subcomment.setSubcommentTime(System.currentTimeMillis());
        subcommentService.save(subcomment);
        return R.success(subcomment);
    }

}
