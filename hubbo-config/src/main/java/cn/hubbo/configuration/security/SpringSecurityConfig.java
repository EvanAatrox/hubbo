package cn.hubbo.configuration.security;

import cn.hubbo.security.filter.FormAndJsonLoginFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationConfiguration configuration) throws Exception {
        List<String> ignorePathPatterns = List.of("/test/**", "/user/login");
        FormAndJsonLoginFilter formAndJsonLoginFilter = new FormAndJsonLoginFilter(authenticationManager(configuration));
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .passwordManagement(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager(configuration))
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorization -> authorization.requestMatchers(ignorePathPatterns.toArray(new String[]{})).permitAll().anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(loginFilter(authenticationManager(configuration)), UsernamePasswordAuthenticationFilter.class);
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
        return loginFilter;
    }


}
