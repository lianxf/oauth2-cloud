package cn.lianxf.oauth.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className OauthTypeEnum
 * @description oauth 2.0 认证类型
 * @date 2019/12/19 16:31
 */
@Getter
@AllArgsConstructor
public enum  OauthTypeEnum {
    /**
     * 全部
     */
    ALL("all"),
    /**
     * 授权码模式 安全性最高
     * 这种模式算是正宗的oauth2的授权模式
     * 设计了auth code，通过这个code再获取token
     * 支持refresh token
     */
    AUTHORIZATION_CODE("authorization_code"),
    /**
     * 客户端模式 基于 clientId secret 进行获取token 认证服务器完全信任客户端 使用于内部系统认证
     * 这种模式直接根据client的id和密钥即可获取token，无需用户参与
     * 这种模式比较合适消费api的后端服务，比如拉取一组用户信息等
     * 不支持refresh token，主要是没有必要
     */
    CLIENT_CREDENTIALS("client_credentials"),
    /**
     * 简易模式
     * 这种模式比授权码模式少了code环节，回调url直接携带token
     * 这种模式的使用场景是基于浏览器的应用
     * 这种模式基于安全性考虑，建议把token时效设置短一些
     * 不支持refresh token
     */
    IMPLICIT("implicit"),
    /**
     * 密码模式
     * 这种模式是最不推荐的，因为client可能存了用户密码
     * 这种模式主要用来做遗留项目升级为oauth2的适配方案
     * 当然如果client是自家的应用，也是可以
     * 支持refresh token
     */
    PASSWORD("password");

    String value;
}
