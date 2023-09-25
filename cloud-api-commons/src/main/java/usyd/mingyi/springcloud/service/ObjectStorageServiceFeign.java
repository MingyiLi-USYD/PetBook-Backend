package usyd.mingyi.springcloud.service;

import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import usyd.mingyi.springcloud.config.FeignConfig;
import usyd.mingyi.springcloud.config.MultipartSupportConfig;

import java.util.List;
@FeignClient(value = "object-storage-service",configuration = {FeignConfig.class})
public interface ObjectStorageServiceFeign {

    @PostMapping(value = "/oss/post/saveImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<String> savePostImages(@RequestPart("files")MultipartFile[] images);

    @PostMapping("/oss/pet/saveImage")
    String savePetImage(@RequestPart("file") MultipartFile image);

    @PostMapping("/oss/user/saveImage")
    String saveUserImage(@RequestPart("file") MultipartFile image);
}
