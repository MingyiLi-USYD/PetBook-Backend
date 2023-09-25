package usyd.mingyi.springcloud.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comment")
public class Comment implements Serializable {
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long commentId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private Long postId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull
    private Long targetUserId; //冗余字段
    private Long commentTime;
    @NotBlank(message = "评论不能为空")
    private String commentContent;
    private Long commentLove;
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

}
