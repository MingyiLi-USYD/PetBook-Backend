package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.PostImageMapper;
import usyd.mingyi.springcloud.pojo.PostImage;

@Service
public class PostImageServiceImp  extends ServiceImpl<PostImageMapper, PostImage> implements PostImageService {

}
