package cn.hubbo.configuration.web;

import cn.hubbo.common.json.GsonRedisSerializer;
import cn.hubbo.utils.common.JsonUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
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

    @Value("${spring.data.redis.password}")
    private String pass;


    /**
     * @return HTTP报文消息转换器
     */
    @Bean
    public HttpMessageConverter<Object> httpMessageConverter() {
        return new GsonHttpMessageConverter(JsonUtils.getStrategiesGson());
    }


    @Bean
    public Gson gson() {
        return JsonUtils.getStrategiesGson();
    }

    @ConditionalOnMissingBean(name = "redisTemplate")
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> keySerializer = RedisSerializer.string();
        GsonRedisSerializer valueSerializer = new GsonRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.setExposeConnection(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


}
