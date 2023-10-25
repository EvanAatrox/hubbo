package cn.hubbo.common.domain.to.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.common.domain.properties
 * @date 2023/10/25 23:40
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */

@Component
@ConfigurationProperties(prefix = "token")
@Data
public class JWTProperties {


    Map<String, Object> header = new HashMap<>();


    /* 类型 */
    private String type = "JWT";


    /* 使用的加密算法 */
    private String algorithm;


    /* token的有效期,单位为秒 */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration interval;


    /* token加密的密钥 */
    private String secretKey;

    @PostConstruct
    public void init() {
        header.put("typ", type);
        header.put("alg", algorithm);
    }


}
