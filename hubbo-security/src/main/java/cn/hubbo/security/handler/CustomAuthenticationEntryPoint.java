package cn.hubbo.security.handler;

import cn.hubbo.common.exception.security.CaptchaNotValidException;
import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.domain.vo.Result;
import cn.hubbo.utils.lang.base.ClientInfo;
import cn.hubbo.utils.web.ServletUtils;
import cn.hutool.core.net.NetUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 张晓华
 * @date 2023-10-23 11:08
 * @usage 认证失败异常处理器
 */
@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    /**
     * 认证失败的回调
     * TODO 相同的一段时间内同一账户认证失败次数达到5次之后就需要锁定账户，24小时之后恢复
     *
     * @param request       请求
     * @param response      响应
     * @param authException 异常信息
     *
     * @throws IOException      异常信息
     * @throws ServletException 异常信息
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("认证失败信息", authException);
        String detailMessage = "认证失败";
        // TODO 待完善
        if (authException instanceof BadCredentialsException) {
            detailMessage = "账号或者密码错误";
        } else if (authException instanceof InsufficientAuthenticationException) {
            // 未提交用户登录名或者密码,或者验证码等信息才会抛出这个错误
            detailMessage = "请检查登录参数是否齐全";
        } else if (authException instanceof AccountStatusException) {
            // 账号状态异常
            detailMessage = "账号已经被锁定,无法进行操作";
        } else if (authException instanceof CaptchaNotValidException) {
            detailMessage = "请检查验证码是否有误";
        }
        addRequestToRestrictedAccessList(request);
        Result result = new Result(ResponseStatusEnum.ERROR).add("detail", detailMessage);
        ServletUtils.reposeObjectWithJson(response, result);
    }


    /**
     * @param request 记录下客户端的错误请求信息,超出限制后服务端将会拒绝与客户端相同IP的所有请求
     */
    private void addRequestToRestrictedAccessList(HttpServletRequest request) {
        String clientIP = ServletUtils.getClientIP(request);
        if (!NetUtil.isInnerIP(clientIP)) {
            ClientInfo clientInfo = ServletUtils.getClientInfo(request);
            // TODO 记录异常信息和客户端信息
            log.info("记录下一次异常行为,客户端信息{}", clientInfo);
        }
    }


}
