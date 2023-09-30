package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usyd.mingyi.common.pojo.PostImage;
import usyd.mingyi.springcloud.mapper.PostImageMapper;


import java.util.List;

@Service
public class PostImageServiceImp  extends ServiceImpl<PostImageMapper, PostImage> implements PostImageService {
    @Autowired
    PostImageMapper postImageMapper;


    @Override
    @Transactional
    public Boolean saveImages(List<PostImage> postImages) {
        for (PostImage postImage : postImages) {
            postImageMapper.insert(postImage);
        }
        return true;
    }
}
