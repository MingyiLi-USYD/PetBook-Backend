package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.PetImage;
import usyd.mingyi.springcloud.service.PetService;
import usyd.mingyi.springcloud.utils.BaseContext;

@RestController
@Slf4j
public class PetImageController {
    @Autowired
    PetService petService;

    @PostMapping("/pet/image/{petId}")
    public R<PetImage> uploadImage(@PathVariable("petId") Long petId, @RequestBody @Validated PetImage petImage) {
        Long currentId = BaseContext.getCurrentId();
        PetImage newPetImage = petService.saveImageForPet(petId, currentId, petImage);
        return R.success(newPetImage);
    }

    @DeleteMapping("/pet/image")
    public R<String> deleteImage(@RequestBody PetImage image) {
        petService.deleteImageForPet(image.getPetId(), BaseContext.getCurrentId(), image.getImageId());
        return R.success("Success to delete this image");
    }


}
