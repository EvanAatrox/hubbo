package cn.hubbo.configuration.security;

import cn.hubbo.security.filter.AccessDecisionManagerImpl;
import cn.hubbo.security.filter.CustomFilterInvocationSecurityMetadataSource;
import cn.hubbo.security.filter.DynamicFilter;
import cn.hubbo.security.filter.FormAndJsonLoginFilter;
import cn.hubbo.security.filter.JwtTokenFilter;
import cn.hubbo.security.handler.AccessDeniedHandlerImpl;
import cn.hubbo.security.handler.AuthenticationEntryPointImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.configuration.security
 * @date 2023/10/20 23:51
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */

@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    private AuthenticationConfiguration authenticationConfiguration;


    /**
     * @return 密码加密工具
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * @return 用户信息校验的工具
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        List<String> ignorePathPatterns = List.of("/test/**", "/user/login");
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .passwordManagement(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorization -> authorization.requestMatchers(ignorePathPatterns.toArray(new String[]{}))
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(web -> web
                        .accessDeniedHandler(accessDeniedHandler())
                        .authenticationEntryPoint(authenticationEntryPoint()))
                //.addFilterBefore(dynamicFilter(), FilterSecurityInterceptor.class)
                .addFilterBefore(oncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(loginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    /**
     * @return 放行的资源
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        List<String> ignorePathPatterns = List.of("/test/**", "/user/login");
        return web -> web.ignoring().requestMatchers(ignorePathPatterns.toArray(new String[]{}));
    }

    @Bean
    public FormAndJsonLoginFilter loginFilter(AuthenticationManager authenticationManager) {
        FormAndJsonLoginFilter loginFilter = new FormAndJsonLoginFilter(authenticationManager);
        loginFilter.setPostOnly(true);
        loginFilter.setFilterProcessesUrl("/user/login");
        loginFilter.setAuthenticationManager(authenticationManager);
        loginFilter.setAllowSessionCreation(false);
        return loginFilter;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }


    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPointImpl();
    }

    @Bean
    public OncePerRequestFilter oncePerRequestFilter() {
        return new JwtTokenFilter(userDetailsService);
    }


    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }

    
    public AccessDecisionManager accessDecisionManager() {
        return new AccessDecisionManagerImpl();
    }

    public DynamicFilter dynamicFilter() {
        return new DynamicFilter(filterInvocationSecurityMetadataSource(), accessDecisionManager());
    }


}
