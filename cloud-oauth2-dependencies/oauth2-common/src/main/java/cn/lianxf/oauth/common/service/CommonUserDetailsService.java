package cn.lianxf.oauth.common.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.lianxf.oauth.common.domain.UserInfo;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * @author beyond09.hik
 * @version 1.0
 * @className CommonUserDetailsService
 * @description  用户身份信息验证
 * @date 2019/12/19 19:02
 */
@Service
public class CommonUserDetailsService implements UserDetailsService {

    /**
     * 验证用户身份信息
     * @param username 用户名
     * @return cn.lianxf.oauth.common.domain.UserInfo {@link UserInfo} 是UserDetails 的实现类
     * @throws UsernameNotFoundException 用户未找到
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserInfo.builder().id("1001").username("admin").password("admin").accountNonExpired(true).accountNonLocked(true)
                .credentialsNonExpired(true).enable(true)
                .authorities(CollectionUtil.toCollection(AuthorityUtils.createAuthorityList("Admin")))
                .build();
    }
}
