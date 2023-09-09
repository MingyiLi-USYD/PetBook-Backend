package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.LovePostMapper;
import usyd.mingyi.springcloud.pojo.LovePost;

@Service
public class LovePostServiceImp extends ServiceImpl<LovePostMapper, LovePost> implements LovePostService {


}
