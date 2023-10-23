package cn.hubbo.security.filter;

import cn.hubbo.common.constant.WebConstants;
import cn.hubbo.common.to.SecurityUser;
import cn.hubbo.domain.dos.User;
import cn.hubbo.utils.security.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author 张晓华
 * @date 2023-10-23 14:08
 * @usage 当前类的用途描述
 */
@Component
public class JwtLoginFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(WebConstants.TOKEN_HEADER);
        if (StringUtils.isNotBlank(headerValue)) {
            // 验证token
            String token = headerValue.replace(WebConstants.TOKEN_VALUE_PREFIX, "");
            // 此处可能会抛出异常
            User user = JWTUtils.verifyToken(token, User.class);
            SecurityUser securityUser = new SecurityUser();
            securityUser.setUserDetail(user);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);

    }


}
