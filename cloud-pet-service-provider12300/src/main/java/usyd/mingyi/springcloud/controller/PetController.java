package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.service.PetService;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@RestController
@Slf4j
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping("/pet")
    public R<String> addPet(@RequestBody Pet pet) {
/*        for (int i = 0; i < 1000000; i++) {
            petService.save(pet);
            pet.setPetId(null);
        }*/
        boolean save = petService.save(pet);
        if (save){
            return R.success("成功了");
        }else {
            return R.error("插入失败");
        }
    }

    @GetMapping("/pet/{petId}")
    public R<Pet> getPet(@PathVariable("petId") Long petId) {
        Long userId = BaseContext.getCurrentId();
        Pet pet = petService.getById(petId);
         if(pet==null||!pet.getUserId().equals(userId)){
             throw new CustomException("没有这个宠物");
         }
         return R.success(pet);
    }
    @DeleteMapping("/pet/{petId}")
    public R<String> deletePet(@PathVariable("petId") Long petId) {
        Long userId = BaseContext.getCurrentId();
        petService.deletePet(petId,userId);

        return R.success("Delete success");
    }

    @PutMapping("/pet/{petId}")
    public R<String> updatePet(@PathVariable("petId") Long petId, @RequestBody Pet pet) {
        Pet targetPet = petService.getById(petId);
        if (targetPet == null) return R.error("No such pet");
        if (!targetPet.getUserId().equals(BaseContext.getCurrentId())) {
            return R.error("No right to update this pet");
        }
        if (petService.updateById(pet)) {
            return R.success("Update Success");
        } else {
            return R.error("Fail to update");
        }
    }


    @GetMapping("/pet/my")
    public R<List<Pet>> getPetList() {
        long id = BaseContext.getCurrentId();
        List<Pet> petList = petService.getPetList(id);
        return R.success(petList);
    }
}