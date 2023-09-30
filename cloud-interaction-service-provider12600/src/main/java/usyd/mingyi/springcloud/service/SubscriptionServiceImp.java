package usyd.mingyi.springcloud.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import usyd.mingyi.common.pojo.Subscription;
import usyd.mingyi.springcloud.mapper.SubscriptionMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImp extends ServiceImpl<SubscriptionMapper, Subscription> implements SubscriptionService  {

    @Override
    public List<String> getAllSubscribes(Long userId) {
        return this.getSubscriptions(Subscription::getMyId,userId);
    }

    @Override
    public List<String> getAllSubscribers(Long userId) {
        return this.getSubscriptions(Subscription::getSubscribedUserId,userId);
    }

    public List<String> getSubscriptions(SFunction<Subscription, ?> field, Long userId){
        return this.list(
                        new LambdaQueryWrapper<Subscription>().eq(field,userId)
                )
                .stream().map(Subscription::getSubscribedUserId)
                .map(String::valueOf).collect(Collectors.toList());
    }
}
