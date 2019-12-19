package cn.lianxf.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className OauthServerApplication
 * @description 认证授权服务端
 * @date 2019/12/19 6:13
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OauthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthServerApplication.class, args);
    }
}
