package cn.hubbo.security.filter;

import cn.hubbo.common.constant.WebConstants;
import cn.hubbo.common.domain.to.SecurityUser;
import cn.hubbo.utils.security.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtTokenFilter extends OncePerRequestFilter {


    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, Object> ops;

    public JwtTokenFilter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.ops = redisTemplate.opsForValue();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(WebConstants.TOKEN_HEADER);
        if (StringUtils.isNotBlank(headerValue)) {
            // 验证token
            String token = headerValue.replace(WebConstants.TOKEN_VALUE_PREFIX, "");
            // 此处可能会抛出异常
            SecurityUser securityUser = (SecurityUser) ops.get(JWTUtils.verifyToken(token, String.class));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }


}
