package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import usyd.mingyi.common.common.R;
import usyd.mingyi.common.pojo.PetImage;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.service.PetImageService;
import usyd.mingyi.springcloud.service.PetService;


import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class PetImageController {
    @Autowired
    PetService petService;

    @Autowired
    PetImageService petImageService;



    @PostMapping("/images/pet")
    public R<PetImage> uploadImage(@RequestBody @Valid PetImage petImage) {
        Long currentId = BaseContext.getCurrentId();
        PetImage newPetImage = petService.saveImageForPet(currentId, petImage);
        return R.success(newPetImage);
    }

    @GetMapping("/images/pet/{petId}")
    public R<List<PetImage>> getPetImages(@PathVariable("petId") Long petId) {
        return R.success(petImageService.getAllPetImages(petId));
    }

    @DeleteMapping("/images/pet/{imageId}")
    public R<String> deleteImageByImageId(@PathVariable("imageId") Long imageId) {
        petService.deleteImageForPet(imageId);
        return R.success("Success to delete this image");
    }


}
