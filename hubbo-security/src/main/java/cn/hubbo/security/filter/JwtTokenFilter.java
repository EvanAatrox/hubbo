package cn.hubbo.security.filter;

import cn.hubbo.common.constant.WebConstants;
import cn.hubbo.common.to.SecurityUser;
import cn.hubbo.utils.security.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtTokenFilter extends OncePerRequestFilter {


    private UserDetailsService userDetailsService;

    public JwtTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(WebConstants.TOKEN_HEADER);
        if (StringUtils.isNotBlank(headerValue)) {
            // 验证token
            String token = headerValue.replace(WebConstants.TOKEN_VALUE_PREFIX, "");
            // 此处可能会抛出异常
            SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(JWTUtils.verifyToken(token, String.class));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }


}
