package cn.lianxf.discovery.server.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className WebSecurityConfig
 * @description eureka web 管理界面安全设置 开启http basic 解决客户端注册失败问题
 * @date 2019/12/19 5:50
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.csrf().disable();
        //注意：为了可以使用 http://${user}:${password}@${host}:${port}/eureka/ 这种方式登录,所以必须是httpBasic,如果是form方式,不能使用url格式登录
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            // 测试环境不使用加密
            .inMemoryAuthentication().passwordEncoder(new PasswordEncoder() {
            public String encode(CharSequence charSequence) {
                return (String) charSequence;
            }

            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.equals(s);
            }
        })
            .withUser("admin").password("admin").roles("ADMIN");
    }
}
