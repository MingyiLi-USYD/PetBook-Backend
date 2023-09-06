package usyd.mingyi.springcloud.authority;

import org.springframework.security.core.GrantedAuthority;
import usyd.mingyi.springcloud.pojo.User;

import java.util.Collection;
import java.util.List;

public interface AuthorityConfigure {
     List<? extends GrantedAuthority> generate (User user);
}
