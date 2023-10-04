package usyd.mingyi.sprincloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment_freeze")
public class CommentFreeze {
    public final static Integer TRY = 0;
    public final static Integer CONFIRM = 1;
    public final static Integer CANCEL = 2;
    @TableId
    String xid;

    Long userId;

    Long commentId;


    Integer state;



}
