package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import usyd.mingyi.common.pojo.PostImage;


import java.util.List;

public interface PostImageService  extends IService<PostImage> {
    Boolean saveImages(List<PostImage> postImages);
}
