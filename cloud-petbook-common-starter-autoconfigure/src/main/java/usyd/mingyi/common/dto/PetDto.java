package usyd.mingyi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usyd.mingyi.common.pojo.Pet;
import usyd.mingyi.common.pojo.PetImage;


import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto extends Pet {
    private List<PetImage> petImage;
}
