package cn.lianxf.oauth.common.config.token;

import cn.hutool.json.JSONUtil;
import cn.lianxf.oauth.common.constant.AuthConstant;
import cn.lianxf.oauth.common.domain.UserInfo;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @author beyond09.hik
 * @version 1.0
 * @className JwtAccessToken
 * @description TODO
 * @date 2019/12/19 18:25
 */
public class JwtAccessToken extends JwtAccessTokenConverter {

    /**
     * 生成token
     * @author beyond09.hik
     * @date 18:44 2019/12/19
     * @param accessToken {@link OAuth2AccessToken}
     * @param authentication {@link OAuth2Authentication}
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken auth2AccessToken = new DefaultOAuth2AccessToken(accessToken);

        // 设置额外用户信息
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();

        // 将用户信息添加到token额外信息中
        auth2AccessToken.getAdditionalInformation().put(AuthConstant.USER_INFO, userInfo);
        return super.enhance(auth2AccessToken, authentication);
    }

    /**
     * 解析token
     * @author beyond09.hik
     * @date 18:46 2019/12/19
     * @param value the value
     * @param map t
     * @return org.springframework.security.oauth2.common.OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        convertData(oauth2AccessToken, oauth2AccessToken.getAdditionalInformation());
        return oauth2AccessToken;
    }

    private void convertData(OAuth2AccessToken accessToken, Map<String, ?> map) {
        accessToken.getAdditionalInformation().put(AuthConstant.USER_INFO,convertUserData(map.get(AuthConstant.USER_INFO)));

    }

    private UserInfo convertUserData(Object map) {
        String json = JSONUtil.toJsonStr(map);
        return JSONUtil.toBean(json, UserInfo.class);
    }
}