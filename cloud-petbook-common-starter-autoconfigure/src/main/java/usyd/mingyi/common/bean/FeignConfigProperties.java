package usyd.mingyi.common.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("petbook.feign")
public class FeignConfigProperties {
    private String userIdKeyName = "X-UserId";

    private String usernameKeyName = "X-Username";

    public String getUserIdKeyName() {
        return userIdKeyName;
    }

    public void setUserIdKeyName(String userIdKeyName) {
        this.userIdKeyName = userIdKeyName;
    }

    public String getUsernameKeyName() {
        return usernameKeyName;
    }

    public void setUsernameKeyName(String usernameKeyName) {
        this.usernameKeyName = usernameKeyName;
    }
}
