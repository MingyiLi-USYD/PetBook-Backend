package usyd.mingyi.springcloud.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@TableName("post_image")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PostImage implements Serializable {
    @TableId("image_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long imageId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "Must contain postId")
    Long postId;
    @NotBlank(message = "Must have imageUrl")
    String imageUrl;
/*    @NotBlank(message = "Must have fileName")
    String fileName;*/
    @TableLogic
    Boolean isDeleted;
}
