package usyd.mingyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.common.pojo.FriendRequest;
import usyd.mingyi.common.pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto extends FriendRequest {
    User friendInfo;
}
