package usyd.mingyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.common.pojo.Subcomment;
import usyd.mingyi.common.pojo.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcommentDto extends Subcomment {
    private User subcommentUser;
    private User relpyUser;
}
