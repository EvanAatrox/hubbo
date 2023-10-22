package cn.hubbo.common.to;

import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.UserStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.common.to
 * @date 2023/10/22 16:49
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Data
@Accessors(chain = true)
public class SecurityUser implements UserDetails {


    private User userDetail;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("admin"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userDetail.getPassword();
    }

    @Override
    public String getUsername() {
        return userDetail.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userDetail.getAccountStatus().equals(UserStatusEnum.DEFAULT);
    }
}
