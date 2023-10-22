package cn.hubbo.configuration.web;

import cn.hubbo.utils.common.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.configuration.web
 * @date 2023/10/20 23:53
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Configuration
public class WebConfiguration {


    /**
     * @return HTTP报文消息转换器
     */
    @Bean
    public HttpMessageConverter<Object> httpMessageConverter() {
        return new GsonHttpMessageConverter(JsonUtils.getStrategiesGson());
    }


}
