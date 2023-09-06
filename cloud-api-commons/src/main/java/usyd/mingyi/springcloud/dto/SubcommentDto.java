package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.Subcomment;
import usyd.mingyi.springcloud.pojo.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubcommentDto extends Subcomment {
    private User subcommentUser;
}
