package usyd.mingyi.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.pojo.UserInfo;


@FeignClient(value = "user-service-base-provider",configuration = FeignConfig.class)
public interface UserBaseServiceFeign {


    @GetMapping("/comment/love/{commentId}")
    String addLovedCommentId(@PathVariable("commentId") Long commentId);

    @DeleteMapping("/comment/love/{commentId}")
    String removeLovedCommentId(@PathVariable("commentId") Long commentId);

    @GetMapping("/subcomment/love/{subcommentId}")
    String addLovedSubcommentId(@PathVariable("subcommentId") Long subcommentId);

    @DeleteMapping("/subcomment/love/{subcommentId}")
    String removeLovedSubcommentId(@PathVariable("subcommentId") Long subcommentId);

    @GetMapping("/user/userInfo")
    UserInfo getUserInfo();
}
