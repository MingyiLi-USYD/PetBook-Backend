package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.springcloud.pojo.PetImage;

import java.util.List;

public interface PetImageService extends IService<PetImage> {
    List<PetImage> getAllPetImages(Long petId);
}
