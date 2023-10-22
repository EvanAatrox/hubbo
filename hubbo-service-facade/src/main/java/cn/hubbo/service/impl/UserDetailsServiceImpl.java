package cn.hubbo.service.impl;

import cn.hubbo.common.to.SecurityUser;
import cn.hubbo.domain.dos.User;
import cn.hubbo.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
        return new SecurityUser().setUserDetail(user);
    }

}
