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

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("mention")
public class Mention implements Serializable {
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long mentionId;
    private Long postId;
    private Long userId;
    private Long targetUserId;
    private Boolean isRead;
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

    public Mention(Long postId, Long userId,Long targetUserId) {
        this.postId = postId;
        this.userId = userId;
        this.targetUserId = targetUserId;
    }
}
