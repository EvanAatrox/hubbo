package cn.hubbo.web.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.web.configuration
 * @date 2023/10/21 0:07
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@EnableAsync
@EnableJpaRepositories("cn.hubbo.dao")
@EntityScan(value = "cn.hubbo.domain.dos")
@Configuration
public class GlobalConfiguration {


}
