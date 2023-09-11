package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.mapper.PetImageMapper;
import usyd.mingyi.springcloud.mapper.PetMapper;
import usyd.mingyi.springcloud.pojo.Pet;
import usyd.mingyi.springcloud.pojo.PetImage;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.util.List;

@Service
public class PetImageServiceImp extends ServiceImpl<PetImageMapper, PetImage> implements PetImageService {
    @Autowired
    PetMapper petMapper;
    @Override
    public List<PetImage> getAllPetImages(Long petId) {
        Pet pet = petMapper.selectById(petId);
        if(pet==null||!pet.getUserId().equals(BaseContext.getCurrentId())){
            throw new CustomException("No pet found");
        }
        return this.list(new LambdaQueryWrapper<PetImage>().eq(PetImage::getPetId,petId));
    }
}
