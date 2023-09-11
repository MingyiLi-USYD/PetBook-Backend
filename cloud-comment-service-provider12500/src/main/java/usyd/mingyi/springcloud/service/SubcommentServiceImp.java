package usyd.mingyi.springcloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.SubcommentMapper;
import usyd.mingyi.springcloud.pojo.Subcomment;


@Service
public class SubcommentServiceImp extends ServiceImpl<SubcommentMapper, Subcomment> implements SubcommentService {

}
