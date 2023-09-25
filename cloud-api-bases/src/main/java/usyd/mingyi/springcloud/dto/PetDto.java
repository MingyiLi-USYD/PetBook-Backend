package usyd.mingyi.springcloud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.pojo.PetImage;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto extends Pet {
    private List<PetImage> petImage;
}
