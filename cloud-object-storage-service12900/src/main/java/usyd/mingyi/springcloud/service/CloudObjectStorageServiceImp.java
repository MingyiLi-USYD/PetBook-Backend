package usyd.mingyi.springcloud.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
// 可以把文件存到firebase 或者 ASW 等云存储商中  但是现在就先存到本地就行了
// 不放入容器
public class CloudObjectStorageServiceImp implements ObjectStorageService{

    @Override
    public String save(MultipartFile file,String dir) {
        return null;
    }

    @Override
    public List<String> saveBatch(List<MultipartFile> files,String dir) {
        return null;
    }
}
