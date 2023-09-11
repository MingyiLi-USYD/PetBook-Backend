package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.dto.FriendRequestDto;

import java.util.List;

@RestController
@Slf4j
public class FriendRequestController {

    @GetMapping("/friendRequests")
    public R<List<FriendRequestDto>> getAllRequests(){

        return R.success(null);
    }
}
