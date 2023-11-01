package cn.hubbo.security.filter;

import cn.hubbo.common.constant.RedisConstants;
import cn.hubbo.common.constant.WebConstants;
import cn.hubbo.common.domain.to.JWTObject;
import cn.hubbo.common.domain.to.JWTTokenBuilder;
import cn.hubbo.common.domain.to.SecurityUser;
import cn.hubbo.common.domain.to.properties.JWTProperties;
import cn.hubbo.domain.dos.User;
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

    private JWTProperties jwtProperties;

    public JwtTokenFilter(RedisTemplate<String, Object> redisTemplate, JWTProperties jwtProperties) {
        this.redisTemplate = redisTemplate;
        this.ops = redisTemplate.opsForValue();
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerValue = request.getHeader(WebConstants.TOKEN_HEADER);
        if (StringUtils.isNotBlank(headerValue)) {
            // 验证token
            String token = headerValue.replace(WebConstants.TOKEN_VALUE_PREFIX, "");
            JWTObject jwtObject = JWTTokenBuilder.parseJWTToken(token, jwtProperties);
            if (jwtObject.isLegal()) {
                String uuid = jwtObject.getUuid();
                String storageToken = (String) this.ops.get(RedisConstants.USER_TOKEN_CACHE_PREFIX.concat(uuid));
                // 和服务端token比对，后期需要进行token的需求操作
                if (token.equals(storageToken)) {
                    User user = (User) this.ops.get(RedisConstants.USER_TOKEN_PREFIX.concat(jwtObject.getUsername()));
                    SecurityUser securityUser = new SecurityUser();
                    securityUser.setUserDetail(user);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }


}
