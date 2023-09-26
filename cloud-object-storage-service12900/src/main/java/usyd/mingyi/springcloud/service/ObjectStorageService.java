package usyd.mingyi.springcloud.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjectStorageService {
    String save(MultipartFile file,String dir);

    List<String> saveBatch(List<MultipartFile> files,String dir);


}
