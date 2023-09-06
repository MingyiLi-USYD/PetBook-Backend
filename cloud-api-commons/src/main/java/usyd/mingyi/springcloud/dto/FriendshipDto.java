package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.Friendship;
import usyd.mingyi.springcloud.pojo.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto extends Friendship {
    User friendInfo;
}
