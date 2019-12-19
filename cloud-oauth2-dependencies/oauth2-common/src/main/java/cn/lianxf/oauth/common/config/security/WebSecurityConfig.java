package cn.lianxf.oauth.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className WebSecurityConfig
 * @description spring web 安全设置
 * @date 2019/12/19 19:14
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService commonUserDetailsService;


    /**
     * 安全访问机制
     * @author beyond09.hik
     * @date 19:17 2019/12/19
     * @param http {@link HttpSecurity}
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 配置登陆页/login并允许访问
        http.formLogin().permitAll()
            // 登出页
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
            // 其余所有请求全部需要鉴权认证
            .and().authorizeRequests().anyRequest().authenticated()
            // 由于使用的是JWT，我们这里不需要csrf
            .and().csrf().disable();
    }

    /**
     *  用户验证
     * @author beyond09.hik
     * @date 19:16 2019/12/19
     * @param auth {@link AuthenticationManagerBuilder}
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(commonUserDetailsService);
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        // 使用BCrypt进行密码的hash
        provider.setPasswordEncoder(new BCryptPasswordEncoder(6));
        return provider;
    }
}