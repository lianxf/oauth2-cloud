package cn.lianxf.oauth.common.config.token;

import cn.lianxf.oauth.common.constant.AuthConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className TokenConfig
 * @description Token 相关配置
 * @date 2019/12/19 17:41
 */
@Configuration
public class TokenConfiguration {

    @Bean
    @Primary
    public TokenStore jdbcTokenStore(DataSource dataSource){
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(TokenStore.class)
    @ConditionalOnBean(RedisConnectionFactory.class)
    public TokenStore redisTokenStore(RedisConnectionFactory redisConnectionFactory){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 使用非对称加密算法来对Token进行签名
     * @author beyond09.hik
     * @date 18:24 2019/12/19
     * @return org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        final JwtAccessTokenConverter converter = new JwtAccessToken();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource(AuthConstant.KEY_STORE), AuthConstant.KEY_PASS.toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(AuthConstant.ALIAS));

        return converter;
    }
}
