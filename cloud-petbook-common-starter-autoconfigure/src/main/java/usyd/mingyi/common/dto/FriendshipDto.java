package usyd.mingyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.common.pojo.Friendship;
import usyd.mingyi.common.pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto extends Friendship {
    User friendInfo;
}
