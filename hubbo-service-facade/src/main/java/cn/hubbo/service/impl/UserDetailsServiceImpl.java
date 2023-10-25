package cn.hubbo.service.impl;

import cn.hubbo.common.domain.to.SecurityUser;
import cn.hubbo.domain.dos.User;
import cn.hubbo.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.service.impl
 * @date 2023/10/20 23:52
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.queryUserByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户信息不存在");
        }
        SecurityUser securityUser = new SecurityUser().setUserDetail(user);
        Set<GrantedAuthority> set = Stream.of("admin:create", "admin:update", "admin:delete").map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        securityUser.setAuthorities(set);
        return securityUser;
    }

}
