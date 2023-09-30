package usyd.mingyi.common.pojo;

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

@TableName("pet_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetImage implements Serializable {
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    Long imageId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "No petId related")
    Long petId;
    @NotBlank(message = "Pet imageUrl can not be blank")
    String imageUrl;
    @TableLogic
    Boolean isDeleted;
}