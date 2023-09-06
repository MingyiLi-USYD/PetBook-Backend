package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.FriendRequest;
import usyd.mingyi.springcloud.pojo.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto extends FriendRequest {
    User friendInfo;
}
