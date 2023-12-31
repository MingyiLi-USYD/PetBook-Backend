package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.pojo.Pet;
import usyd.mingyi.common.utils.BaseContext;

import usyd.mingyi.springcloud.service.PetImageService;
import usyd.mingyi.springcloud.service.PetService;


import java.util.List;

@RestController
@Slf4j
public class PetController {

    @Autowired
    PetService petService;


    @PostMapping("/pet")
    public R<Pet> addPet(@RequestBody Pet pet) {

        boolean save = petService.save(pet);
        if (save){
            return R.success(pet);
        }else {
            return R.error("插入失败");
        }
    }

    @PutMapping("/pet")
    public R<String> updatePet(@RequestBody Pet pet) {
        if(pet.getPetId()==null){
            throw new CustomException("宠物id未知");
        }
        Pet targetPet = petService.getById(pet.getPetId());

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

    @GetMapping("/pet/{petId}")
    public R<Pet> getPet(@PathVariable("petId") Long petId) {
        Long userId = BaseContext.getCurrentId();
        Pet pet = petService.getById(petId);
         if(pet==null){
             throw new CustomException("没有这个宠物");
         }
         if(!pet.getUserId().equals(userId)&&!pet.getPetVisible()){
             throw new CustomException("Can not access this pet");
         }
         return R.success(pet);
    }
    @DeleteMapping("/pet/{petId}")
    public R<String> deletePet(@PathVariable("petId") Long petId) {
        Long userId = BaseContext.getCurrentId();
        petService.deletePet(petId,userId);
        return R.success("Delete success");
    }




    @GetMapping("/pets/my")
    public R<List<Pet>> getPetList() {
        Long userId = BaseContext.getCurrentId();
        List<Pet> petList = petService.getPetList(userId);
        return R.success(petList);
    }
    @GetMapping("/pets/{userId}")
    public R<List<Pet>> getPetListByUserId(@PathVariable("userId") Long userId) {;
        List<Pet> petList = petService.getPetList(userId);
        return R.success(petList);
    }
}
