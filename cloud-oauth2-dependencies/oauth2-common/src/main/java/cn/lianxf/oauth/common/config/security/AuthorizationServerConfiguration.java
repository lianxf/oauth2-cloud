package cn.lianxf.oauth.common.config.security;

import cn.lianxf.oauth.common.config.token.JwtAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className AuthorizationServerConfiguration
 * @description 认证授权服务相关配置
 * @date 2019/12/19 18:04
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService commonUserDetailsService;

    @Autowired
    private TokenStore tokenStore;

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * @author beyond09.hik
     * @date 18:12 2019/12/19
     * @param security the security {@link AuthorizationServerSecurityConfigurer}
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化
     * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息
     * @author beyond09.hik
     * @date 18:16 2019/12/19
     * @param clients {@link ClientDetailsServiceConfigurer}
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
    }

    /**
     *  用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @author beyond09.hik
     * @date 18:13 2019/12/19
     * @param endpoints the endpoints {@link AuthorizationServerEndpointsConfigurer}
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                // 配置JwtAccessToken转换器
                .accessTokenConverter(jwtAccessTokenConverter())
                // refresh_token需要userDetailsService
                .reuseRefreshTokens(false).userDetailsService(commonUserDetailsService)
                .tokenStore(tokenStore);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        return new JwtAccessToken();
    }
}
