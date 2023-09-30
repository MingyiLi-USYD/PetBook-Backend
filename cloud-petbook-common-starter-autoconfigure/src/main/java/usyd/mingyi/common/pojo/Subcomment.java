package usyd.mingyi.common.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("subcomment")
public class Subcomment implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId
    private Long subcommentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)

    private Long commentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    @NotNull
    private Long targetUserId;
    private Long subcommentTime;
    @NotNull
    private String subcommentContent;
    private Long subcommentLove;
    private Boolean isReply;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long replyUserId;

}
