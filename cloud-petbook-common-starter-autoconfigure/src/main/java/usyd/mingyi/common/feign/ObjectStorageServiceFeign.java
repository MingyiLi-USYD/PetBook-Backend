package usyd.mingyi.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import usyd.mingyi.common.component.FeignConfig;


import java.util.List;

@FeignClient(value = "object-storage-service", configuration = {FeignConfig.class})
public interface ObjectStorageServiceFeign {

    @PostMapping(value = "/oss/post/saveImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<String> savePostImages(@RequestPart("files") MultipartFile[] images);

    @PostMapping(value = "/oss/pet/saveImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String savePetImage(@RequestPart("file") MultipartFile image);

    @PostMapping(value = "/oss/user/saveImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String saveUserImage(@RequestPart("file") MultipartFile image);
}
