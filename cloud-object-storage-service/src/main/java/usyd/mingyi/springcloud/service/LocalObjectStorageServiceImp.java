package usyd.mingyi.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import usyd.mingyi.springcloud.utils.ImageUtils;

import java.util.List;

@Service
public class LocalObjectStorageServiceImp implements ObjectStorageService {

    @Autowired
    ImageUtils imageUtils;


    @Override
    public String save(MultipartFile file,String dir) {
        return imageUtils.saveOneImage(file,dir);
    }

    @Override
    public List<String> saveBatch(List<MultipartFile> files,String dir) {
        return imageUtils.saveBatchImages(files,dir);
    }
}
