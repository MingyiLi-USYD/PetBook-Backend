package usyd.mingyi.springcloud.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import usyd.mingyi.common.utils.BaseContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ImageUtils {


    public static final String UserBase = "/user/";
    public static final String PostBase = "/post/";
    public static final String PetBase = "/pet/";

    @Value("${upload.dir}")
    private String projectDir;

    public String saveImage(MultipartFile image, String dir, String targetFileName) {

        String  targetDirectory = projectDir + dir;

        // 检查目标保存路径是否存在，如果不存在则创建目录
        Path targetDirPath = Path.of(targetDirectory);
        if (!Files.exists(targetDirPath)) {
            try {
                Files.createDirectories(targetDirPath);
            } catch (IOException e) {
                e.printStackTrace();
                return "Failed to create target directory.";
            }
        }

        // 获取原始文件名
        String originalFileName = image.getOriginalFilename();

        // 获取文件后缀
        String fileExtension = "";
        int lastDotIndex = originalFileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            fileExtension = originalFileName.substring(lastDotIndex);
        }

        // 使用原始文件名和文件后缀构建目标文件名
        String finalFileName = targetFileName + fileExtension;

        // 构建保存图片的完整路径
        Path filePath = Path.of(targetDirectory, finalFileName);

        try {
            // 将图片文件保存到指定目录
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            // 构建文件的全路径和名字
            return dir+finalFileName;

        } catch (IOException e) {
            e.printStackTrace();
            // 处理保存失败的情况
            return "Failed to upload " + originalFileName;
        }
    }


    public  List<String> saveBatchImages(List<MultipartFile> images,String suffix ){
        String currentId = String.valueOf(BaseContext.getCurrentId());

        List<String> imageNameList = new ArrayList<>();
        for (MultipartFile image : images) {
            String imageName = this.saveImage(image, currentId+suffix, UUID.randomUUID().toString());
            imageNameList.add(imageName);
        }
        return imageNameList;
    }

    public  String saveOneImage(MultipartFile image,String suffix ){

        String currentId = String.valueOf(BaseContext.getCurrentId());

        return this.saveImage(image,  currentId+suffix, UUID.randomUUID().toString());


    }
}
