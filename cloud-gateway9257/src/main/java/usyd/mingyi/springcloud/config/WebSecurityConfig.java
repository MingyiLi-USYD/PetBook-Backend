package usyd.mingyi.springcloud.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig   {

    @Bean
    public SecurityWebFilterChain defaultSecurityFilterChain(ServerHttpSecurity http) {

        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/**")
                .permitAll()
                .and()
                .build();

    }



/*
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("MingyiLi");
        return jwtAccessTokenConverter;

    }*/




}
