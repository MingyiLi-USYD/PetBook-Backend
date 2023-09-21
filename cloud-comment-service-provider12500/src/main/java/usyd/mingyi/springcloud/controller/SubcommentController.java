package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.dto.SubcommentDto;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.service.SubcommentService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class SubcommentController {
    @Autowired
    SubcommentService subcommentService;

    @PostMapping("/subcomment")
    public R<Subcomment> addSubcomment(@RequestBody Subcomment subcomment){
        Long id = BaseContext.getCurrentId();
        subcomment.setUserId(id);
        subcomment.setSubcommentTime(System.currentTimeMillis());
        subcommentService.save(subcomment);
        //需要再查一遍把最新的数据库值返回回去 因为前端传入的subcomment 部分字段是空
        Subcomment byId = subcommentService.getById(subcomment.getSubcommentId());
        return R.success(byId);
    }
    @GetMapping("/subcomments/{commentId}")
    public R<List<Subcomment>> getSubcommentsByCommentId(@PathVariable("commentId") Long commentId){
        List<Subcomment> subcommentDtos = subcommentService.getSubcomments(commentId,false);
        return R.success(subcommentDtos);
    }

    @GetMapping("/subcomment/count/{commentId}")
    public R<Long> countSubcommentSize(@PathVariable("commentId") Long commentId){
        Long count = subcommentService.count(
                new LambdaQueryWrapper<Subcomment>()
                        .eq(Subcomment::getCommentId, commentId)
        );
        return R.success(count);
    }

    @GetMapping("/subcomments/limit/{commentId}")
    public R<List<Subcomment>> getSubcommentsByCommentIdLimit(@PathVariable("commentId") Long commentId){
        List<Subcomment> subcommentDtos = subcommentService.getSubcomments(commentId,true);
        return R.success(subcommentDtos);
    }

}
