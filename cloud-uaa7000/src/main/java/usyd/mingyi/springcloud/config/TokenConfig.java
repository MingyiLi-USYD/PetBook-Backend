package usyd.mingyi.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {
    private String SIGNING_KEY = "MingyiLi";

    @Bean
    public TokenStore tokenStore(){
      return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        jwtAccessTokenConverter.setSigningKey(SIGNING_KEY);
        return jwtAccessTokenConverter;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenEnhancer tokenEnhancer() { // 创建并返回 CustomTokenEnhancer
        return new CustomTokenEnhancer();
    }

}
