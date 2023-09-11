package usyd.mingyi.springcloud.service;

import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.dto.FriendshipDto;

import java.util.List;

@Service
public class FriendshipServiceImp implements FriendshipService {
    @Override
    public List<FriendshipDto> getAllFriends() {
        return null;
    }

    @Override
    public List<FriendshipDto> getAllFriends(Long[] ids) {
        return null;
    }
}
