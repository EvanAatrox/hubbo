package cn.hubbo.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author 张晓华
 * @date 2023-10-19 09:02
 * @usage 当前类的用途描述
 */
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true)
@EnableAsync
public class GlobalConfiguration {
    
    
    
}
