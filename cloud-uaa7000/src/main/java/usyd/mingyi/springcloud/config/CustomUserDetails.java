package usyd.mingyi.springcloud.config;
import org.springframework.security.core.GrantedAuthority;
import usyd.mingyi.springcloud.pojo.User;

import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private String userId; // 额外的字段

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.userId = String.valueOf(user.getUserId()); // 将 userId 添加到 UserDetails
    }

    public String getUserId() {
        return userId;
    }
}
