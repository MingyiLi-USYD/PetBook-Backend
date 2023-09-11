package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.component.PoConvertToDto;
import usyd.mingyi.springcloud.dto.PetDto;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.pojo.PetImage;
import usyd.mingyi.springcloud.service.PetServiceFeign;

import java.util.List;

@RestController
@Slf4j
public class PetController {
    @Autowired
    PetServiceFeign petServiceFeign;
    @Autowired
    PoConvertToDto poConvertToDto;

    @PostMapping("/pet")
    public R<String> addPet(@RequestBody Pet pet) {
        return R.success(petServiceFeign.addPet(pet));
    }

    @PutMapping("/pet")
    public R<String> updatePet(@RequestBody Pet pet) {
        return R.success(petServiceFeign.updatePet(pet));

    }

    @GetMapping("/pets/my")
    public R<List<Pet>> getPetList() {
        return R.success(petServiceFeign.getPetList());
    }


    @GetMapping("/pet/{petId}")
    public R<PetDto> getPet(@PathVariable("petId") Long petId) {
        Pet pet = petServiceFeign.getPet(petId);
        PetDto petDto = poConvertToDto.petToPetDto(pet);
        List<PetImage> petImages = petServiceFeign.getPetImages(petDto.getPetId());
        petDto.setPetImage(petImages);
        return R.success(petDto);
    }

    @DeleteMapping("/pet/{petId}")
    public R<String> deletePet(@PathVariable("petId") Long petId) {
        //逻辑删除宠物 但是照片可以暂时不删除
        return R.success(petServiceFeign.deletePet(petId));

    }

}
