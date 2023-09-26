package usyd.mingyi.springcloud.pojo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableId
    private Long petId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    private String petName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String category;
    private String petAvatar;
    private String petDescription;
    private Boolean petVisible;
    @TableLogic
    private Boolean isDeleted;
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
