package usyd.mingyi.sprincloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.pojo.UserInfo;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.sprincloud.mongodb.service.MongoService;
import usyd.mingyi.sprincloud.mongodb.service.UserInfoService;

@RestController
@Slf4j
public class UserLovedController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    MongoService mongoService;

    @GetMapping("/comment/love/{commentId}")
    public R<String> addLovedCommentId(@PathVariable("commentId") Long commentId) {

        userInfoService.addCommentToLovedComments(BaseContext.getCurrentId(), commentId);

        return R.success("插入成功");

    }

    @DeleteMapping("/comment/love/{commentId}")
    public R<String> removeLovedCommentId(@PathVariable("commentId") Long commentId) {

        userInfoService.removeCommentFromLovedComments(BaseContext.getCurrentId(), commentId);

        return R.success("移除成功");
    }


    @GetMapping("/subcomment/love/{subcommentId}") //先这样放着  没有实现TCC的 seata支持 上面的实现了  TCC太费程序员了
    public R<String> addLovedSubcommentId(@PathVariable("subcommentId") Long subcommentId) {

        if (userInfoService.addSubCommentToLovedSubcomments(BaseContext.getCurrentId(), subcommentId)) {

            return R.error("插入失败");
        }

        return R.success("插入成功");

    }

    @DeleteMapping("/subcomment/love/{subcommentId}")
    public R<String> removeLovedSubcommentId(@PathVariable("subcommentId") Long subcommentId) {

        if (userInfoService.removeSubcommentFromLovedSubcomments(BaseContext.getCurrentId(), subcommentId)) {

            return R.error("移除失败");
        }

        return R.success("移除成功");
    }

    @GetMapping("/user/userInfo")
    public R<UserInfo> getUserInfo() {
        return R.success(userInfoService.getUserInfoByUserId(BaseContext.getCurrentId()));
    }
}
