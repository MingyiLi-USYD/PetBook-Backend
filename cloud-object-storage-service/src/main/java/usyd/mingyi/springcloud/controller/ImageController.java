package usyd.mingyi.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.service.ObjectStorageService;
import usyd.mingyi.springcloud.utils.ImageUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.util.List;

@RestController
public class ImageController {
    @Autowired
    ObjectStorageService objectStorageService;

    @Value("${upload.dir}")
    private String projectDir;

    @PostMapping(value = "/oss/post/saveImages",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<List<String>> savePostImages(@RequestPart("files") List<MultipartFile> images) {
        return R.success(objectStorageService.saveBatch(images,ImageUtils.PostBase));
    }

    @PostMapping("/oss/pet/saveImage")
    public R<String> savePetImage(@RequestPart("file") MultipartFile image) {
        return R.success(objectStorageService.save(image,ImageUtils.PetBase));
    }

    @PostMapping("/oss/user/saveImage")
    public R<String> saveUserImage(@RequestPart("file") MultipartFile image) {
        return R.success(objectStorageService.save(image,ImageUtils.UserBase));
    }

    @GetMapping("/oss/download")
    public void downloadFile(@RequestParam("name")String fileName, HttpServletResponse response){

        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(projectDir + fileName);
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
                outputStream.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
            //throw new RuntimeException(e);
        }
        finally {
            try {
                outputStream.close();
                fileInputStream.close();
            } catch (Exception e) {
                //throw new RuntimeException(e);
            }

        }

    }
}
