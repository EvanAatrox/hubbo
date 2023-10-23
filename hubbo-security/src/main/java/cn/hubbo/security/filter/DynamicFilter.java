package cn.hubbo.security.filter;

import cn.hutool.core.text.AntPathMatcher;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.io.IOException;
import java.util.List;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.security.filter
 * @date 2023/10/23 23:08
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public class DynamicFilter extends AbstractSecurityInterceptor implements Filter {

    private FilterInvocationSecurityMetadataSource invocationSecurityMetadataSource;


    private AccessDecisionManager accessDecisionManager;

    private AntPathMatcher antPathMatcher;


    public DynamicFilter(FilterInvocationSecurityMetadataSource invocationSecurityMetadataSource, AccessDecisionManager accessDecisionManager) {
        this.invocationSecurityMetadataSource = invocationSecurityMetadataSource;
        this.accessDecisionManager = accessDecisionManager;
        super.setAccessDecisionManager(accessDecisionManager);
        this.antPathMatcher = new AntPathMatcher();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest request && servletResponse instanceof HttpServletResponse response) {
            FilterInvocation filterInvocation = new FilterInvocation(request, response, filterChain);
            //直接放行OPTIONS 请求
            if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
            }
            // 放行白名单资源
            List<String> ignorePathPatterns = List.of("/test/**", "/user/**");
            for (String pattern : ignorePathPatterns) {
                if (antPathMatcher.match(pattern, request.getRequestURI().toString())) {
                    filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                    return;
                }
            }
            InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
            try {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            } finally {
                super.afterInvocation(token, null);
            }

        }
    }


    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }


    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return invocationSecurityMetadataSource;
    }


}
