package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.PetImage;
import usyd.mingyi.springcloud.service.PetImageService;
import usyd.mingyi.springcloud.service.PetService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class PetImageController {
    @Autowired
    PetService petService;

    @Autowired
    PetImageService petImageService;



    @PostMapping("/image/pet/{petId}")
    public R<PetImage> uploadImage(@PathVariable("petId") Long petId, @RequestBody @Validated PetImage petImage) {
        Long currentId = BaseContext.getCurrentId();
        PetImage newPetImage = petService.saveImageForPet(petId, currentId, petImage);
        return R.success(newPetImage);
    }

    @GetMapping("/images/pet/{petId}")
    public R<List<PetImage>> getPetImages(@PathVariable("petId") Long petId) {
        return R.success(petImageService.getAllPetImages(petId));
    }

    @DeleteMapping("/pet/image/{imageId}")
    public R<String> deleteImageByImageId(@PathVariable("imageId") Long imageId) {
        petService.deleteImageForPet(imageId);
        return R.success("Success to delete this image");
    }


}
