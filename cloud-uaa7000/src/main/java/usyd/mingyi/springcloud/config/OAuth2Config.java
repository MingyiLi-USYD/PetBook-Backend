package usyd.mingyi.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("cId")
                .resourceIds("PetBook")
                .secret(bCryptPasswordEncoder.encode("secret"))
                .authorizedGrantTypes("refresh_token", "authorization_code",
                        "password", "client_credentials")
                .accessTokenValiditySeconds(3600)
                .scopes("all")
                .autoApprove(false)
                .redirectUris("http://www.baidu.com");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }


    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(new CustomTokenEnhancer());
        tokenEnhancers.add(accessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        services.setTokenEnhancer(tokenEnhancerChain);
        services.setAccessTokenValiditySeconds(7200);
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

}
