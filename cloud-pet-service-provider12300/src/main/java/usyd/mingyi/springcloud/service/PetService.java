package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.pojo.PetImage;

import java.util.List;


public interface PetService extends IService<Pet> {
    List<Pet> getPetList(Long userId);
    void deletePet(Long petId,Long useId);
    PetImage saveImageForPet(Long userId, PetImage petImage);
    void  deleteImageForPet(Long imageId);


}
