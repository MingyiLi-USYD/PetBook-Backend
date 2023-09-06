package usyd.mingyi.springcloud.authority;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import usyd.mingyi.springcloud.pojo.User;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SimpleAuthorityConfigure implements AuthorityConfigure {

    @Override
    public List<? extends GrantedAuthority> generate(User user) {
        String role = user.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 根据不同的角色生成不同的权限列表
        if ("Root".equals(role)) {
            authorities.add(() -> "Root");

        } else if ("User".equals(role)) {
            authorities.add(() -> "User");

        } else if ("Admin".equals(role)) {
            authorities.add(() -> "Admin");
        }

        return authorities;
    }
}
