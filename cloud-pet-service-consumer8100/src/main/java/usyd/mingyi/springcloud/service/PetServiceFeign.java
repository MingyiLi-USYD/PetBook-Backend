package usyd.mingyi.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.Pet;

@Component
@FeignClient(value = "pet-service-provider")
public interface PetServiceFeign {

    @PostMapping("/provider/pet")
    R<String> addPet(@RequestBody Pet pet);
}
