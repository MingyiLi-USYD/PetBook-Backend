package usyd.mingyi.springcloud.config;


import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        // 创建一个包含附加信息的 Map
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("userId", userDetails.getUserId());

        // 将附加信息添加到 Token 的附加声明中
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}
