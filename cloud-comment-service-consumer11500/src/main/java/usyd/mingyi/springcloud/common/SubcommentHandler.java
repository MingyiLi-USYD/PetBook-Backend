package usyd.mingyi.springcloud.common;



import usyd.mingyi.common.dto.SubcommentDto;
import usyd.mingyi.common.pojo.Subcomment;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.utils.ResultHandler;

import java.util.List;

public class SubcommentHandler {
    public static List<SubcommentDto> handleUserInfo(List<Subcomment> subcommentList, List<User> userList) {
        return ResultHandler.mergeObjectLists(subcommentList,
                userList,
                SubcommentDto::setSubcommentUser,
                SubcommentDto::getUserId,
                SubcommentDto::new,
                User::getUserId);
    }

}
