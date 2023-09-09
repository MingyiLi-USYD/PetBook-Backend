package usyd.mingyi.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import usyd.mingyi.springcloud.config.rest.FeignConfig;
import usyd.mingyi.springcloud.pojo.Pet;

import java.util.List;
@FeignClient(value = "pet-service-provider", configuration = FeignConfig.class)
public interface PetServiceFeign {
    @PostMapping("/pet")
    String addPet(@RequestBody Pet pet);

    @GetMapping("/pet/{petId}")
    Pet getPet(@PathVariable("petId") Long petId);

    @DeleteMapping("/pet/{petId}")
    String deletePet(@PathVariable("petId") Long petId);

    @PutMapping("/pet/{petId}")
    String updatePet(@PathVariable("petId") Long petId, @RequestBody Pet pet);

    @GetMapping("/pet/my")
    List<Pet> getPetList();

}
