package cn.hubbo.security.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;


public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (object instanceof FilterInvocation filterInvocation) {
            HttpServletRequest request = filterInvocation.getRequest();
            return SecurityConfig.createList("admin");
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
