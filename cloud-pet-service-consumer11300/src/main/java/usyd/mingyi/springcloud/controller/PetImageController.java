package usyd.mingyi.springcloud.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.feign.ObjectStorageServiceFeign;
import usyd.mingyi.common.feign.PetServiceFeign;
import usyd.mingyi.common.pojo.PetImage;


@RestController
@Slf4j
public class PetImageController {
    @Autowired
    PetServiceFeign petServiceFeign;
    @Autowired
    ObjectStorageServiceFeign objectStorageServiceFeign;


    @DeleteMapping("/pet/image/{imageId}")
    public R<String> deleteImageByImageId(@PathVariable("imageId") Long imageId) {
        petServiceFeign.deleteImageByImageId(imageId);
        return R.success("Success to delete this image");
    }

    @PostMapping("/pet/image/{petId}")
    public R<PetImage> uploadImage(@PathVariable("petId") Long petId,
                                   @RequestParam("image") MultipartFile image) {
        String url = objectStorageServiceFeign.savePetImage(image);
        PetImage petImage = new PetImage();
        petImage.setPetId(petId);
        petImage.setImageUrl(url);

        return R.success(petServiceFeign.uploadImage(petImage));
    }


}
