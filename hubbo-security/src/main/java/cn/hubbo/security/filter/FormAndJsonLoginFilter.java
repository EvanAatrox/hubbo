package cn.hubbo.security.filter;

import cn.hubbo.common.to.SecurityUser;
import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.domain.vo.LoginUserVO;
import cn.hubbo.domain.vo.Result;
import cn.hubbo.utils.common.JsonUtils;
import cn.hubbo.utils.security.JWTUtils;
import cn.hubbo.utils.web.ServletUtils;
import cn.hutool.core.net.NetUtil;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.security.filter
 * @date 2023/10/22 21:17
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public class FormAndJsonLoginFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;


    public FormAndJsonLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        super.setPostOnly(true);
        super.setFilterProcessesUrl("/user/login");
        this.authenticationManager = authenticationManager;
    }


    /**
     * @param request  请求
     * @param response 响应
     * @return 认证信息
     * @throws AuthenticationException 失败信息,异常的明细详见CSDN 程序三两行的博客描述 https://blog.csdn.net/qq_34491508/article/details/126010263
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
            if (Objects.isNull(userVO)) {
                addRequestToRestrictedAccessList(request);
                throw new InsufficientAuthenticationException("无效的登录请求");
            }
            // TODO 校验验证码,IP等信息
            authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(userVO.getUsername(), userVO.getRawPasswd());
        } else {
            String username = ServletUtils.getParameterOrDefault(request, "username");
            String rawPasswd = ServletUtils.getParameterOrDefault(request, "rawPasswd");
            if (StringUtils.isBlank(username) || StringUtils.isEmpty(rawPasswd)) {
                addRequestToRestrictedAccessList(request);
                throw new InsufficientAuthenticationException("无效的登录请求");
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
        System.out.println("认证结果 " + authResult);
        if (authResult instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            if (authenticationToken.getPrincipal() instanceof SecurityUser securityUser) {
                User userDetail = securityUser.getUserDetail();
                Gson strategiesGson = JsonUtils.getStrategiesGson();
                String token = JWTUtils.generateToken(UUID.randomUUID().toString(), strategiesGson.toJson(userDetail));
                Result result = new Result(ResponseStatusEnum.SUCCESS).add("token", token);
                ServletUtils.reposeObjectWithJson(response, result);
            }
        } else {
            chain.doFilter(request, response);
        }
    }


    /**
     * 认证失败的回调
     *
     * @param request  请求
     * @param response 响应
     * @param failed   异常信息
     * @throws IOException      异常信息
     * @throws ServletException 异常信息
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("认证失败信息 " + failed);
        String detailMessage = "认证失败";
        // TODO 待完善
        if (failed instanceof BadCredentialsException) {
            detailMessage = "账号或者密码错误";
        } else if (failed instanceof InsufficientAuthenticationException) {
            // 未提交用户登录名或者密码,或者验证码等信息才会抛出这个错误
            detailMessage = "请检查登录参数是否齐全";
        } else if (failed instanceof AccountStatusException) {
            // 账号状态异常
            detailMessage = "账号已经被锁定,无法进行操作";
        }
        Result result = new Result(ResponseStatusEnum.ERROR).add("detail", detailMessage);
        ServletUtils.reposeObjectWithJson(response, result);
    }

    /**
     * @param request 记录下客户端的错误请求信息,超出限制后服务端将会拒绝与客户端相同IP的所有请求
     */
    private void addRequestToRestrictedAccessList(ServletRequest request) {
        String clientIP = ServletUtils.getClientIP(request);
        if (!NetUtil.isInnerIP(clientIP)) {
            // TODO 记录异常信息和客户端信息
            System.out.println("记录下一次异常行为");
        }
    }


}