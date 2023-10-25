package cn.hubbo.security.filter;

import cn.hubbo.common.constant.RedisConstants;
import cn.hubbo.common.domain.to.SecurityUser;
import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.domain.vo.LoginUserVO;
import cn.hubbo.domain.vo.Result;
import cn.hubbo.utils.security.JWTUtils;
import cn.hubbo.utils.web.ServletUtils;
import cn.hutool.core.util.IdUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.security.filter
 * @date 2023/10/22 21:17
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Slf4j
@SuppressWarnings({"unused"})
public class FormAndJsonLoginFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;


    private final RedisTemplate<String, Object> redisTemplate;


    private final ValueOperations<String, Object> ops;

    /* 用户token信息缓存时间 */
    private Duration duration = Duration.ofDays(1);

    public FormAndJsonLoginFilter(AuthenticationManager authenticationManager, RedisTemplate<String, Object> redisTemplate) {
        super(authenticationManager);
        super.setPostOnly(true);
        super.setFilterProcessesUrl("/user/login");
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        this.ops = redisTemplate.opsForValue();
    }


    /**
     * @param request  请求
     * @param response 响应
     * @return 认证信息
     * @throws AuthenticationException 失败信息,异常的明细详见CSDN 程序三两行的博客描述
     *                                 https://blog.csdn.net/qq_34491508/article/details/126010263
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contentType = request.getContentType();
        LoginUserVO userVO = null;
        UsernamePasswordAuthenticationToken authenticationToken = null;
        if (MediaType.APPLICATION_JSON.includes(MediaType.valueOf(contentType))) {
            try {
                userVO = ServletUtils.getRequiredData(request, LoginUserVO.class);
            } catch (Exception ignored) {
            }
            // 校验提交的登录信息是否有效，无效则抛出不充分的认证信息错误
            if (Objects.isNull(userVO) || StringUtils.isBlank(userVO.getUsername()) || StringUtils.isBlank(userVO.getRawPasswd())) {
                throw new InsufficientAuthenticationException("登录信息不完整");
            }
            //TODO 校验验证码是否一致，否则抛出CaptchaNotValidException
            checkCaptchaIsValid(userVO);
            authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(userVO.getUsername(), userVO.getRawPasswd());
        } else {
            String username = ServletUtils.getParameterOrDefault(request, "username");
            String rawPasswd = ServletUtils.getParameterOrDefault(request, "rawPasswd");
            if (StringUtils.isBlank(username) || StringUtils.isEmpty(rawPasswd)) {
                throw new InsufficientAuthenticationException("登录信息不完整");
            }
            authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(username, rawPasswd);
        }
        this.setDetails(request, authenticationToken);
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 认证成功地回调
     *
     * @param request    请求
     * @param response   响应
     * @param chain      过滤器链
     * @param authResult 认证信息
     * @throws IOException      异常信息
     * @throws ServletException 异常信息
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("认证信息{}", authResult);
        if (authResult instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            if (authenticationToken.getPrincipal() instanceof SecurityUser securityUser) {
                User userDetail = securityUser.getUserDetail();
                ops.set(RedisConstants.USER_TOKEN_PREFIX.concat(userDetail.getUsername()), userDetail, duration);
                String uuid = IdUtil.fastUUID();
                String token = JWTUtils.generateToken(uuid, userDetail.getUsername());
                ops.set(RedisConstants.USER_TOKEN_CACHE_PREFIX.concat(uuid), token, duration);
                Result result = new Result(ResponseStatusEnum.SUCCESS).add("token", token);
                ServletUtils.reposeObjectWithJson(response, result);
            }
        } else {
            chain.doFilter(request, response);
        }
    }


    /**
     * TODO 校验验证码
     *
     * @param loginUserVO 校验验证码是否一致，否则抛出CaptchaNotValidException
     */
    private void checkCaptchaIsValid(LoginUserVO loginUserVO) {

    }


}
