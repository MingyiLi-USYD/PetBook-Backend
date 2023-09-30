package usyd.mingyi.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.component.FeignConfig;
import usyd.mingyi.common.pojo.Pet;
import usyd.mingyi.common.pojo.PetImage;


import java.util.List;
@FeignClient(value = "pet-service-provider", configuration = FeignConfig.class)
public interface PetServiceFeign {
    @PostMapping("/pet")
    Pet addPet(@RequestBody Pet pet);

    @PutMapping("/pet")
    String updatePet(@RequestBody Pet pet);

    @GetMapping("/pet/{petId}")
    Pet getPet(@PathVariable("petId") Long petId);

    @DeleteMapping("/pet/{petId}")
    String deletePet(@PathVariable("petId") Long petId);


    @GetMapping("/pets/my")
    List<Pet> getPetList();

    @GetMapping("/pets/{userId}")
    List<Pet> getPetListByUserId(@PathVariable("userId") Long userId);

    //下面是宠物图片相关的
    @PostMapping("/images/pet")
    PetImage uploadImage(@RequestBody PetImage petImage);

    @GetMapping("/images/pet/{petId}")
    List<PetImage> getPetImages(@PathVariable("petId") Long petId);

    @DeleteMapping("/images/pet/{imageId}")
    String deleteImageByImageId(@PathVariable("imageId") Long imageId);


}
