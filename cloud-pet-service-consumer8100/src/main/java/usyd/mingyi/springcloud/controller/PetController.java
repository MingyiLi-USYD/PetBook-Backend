package usyd.mingyi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.service.PetServiceFeign;

@RestController
@Slf4j
public class PetController {

    @Autowired
    PetServiceFeign petServiceFeign;

    public R<String> addPet(Pet pet){
        return petServiceFeign.addPet(pet);
    }

}
