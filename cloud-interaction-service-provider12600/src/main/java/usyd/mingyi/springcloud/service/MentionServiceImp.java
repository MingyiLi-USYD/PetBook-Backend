package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.mapper.MentionMapper;
import usyd.mingyi.springcloud.pojo.Mention;


@Service
public class MentionServiceImp extends ServiceImpl<MentionMapper, Mention> implements MentionService {


}
