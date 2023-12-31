package usyd.mingyi.common.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    @JsonIgnore
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Long updateUser;

}
