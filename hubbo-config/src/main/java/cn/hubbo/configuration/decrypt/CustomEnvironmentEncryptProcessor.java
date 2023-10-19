package cn.hubbo.configuration.decrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 张晓华
 * @date 2023-10-19 16:26
 * @usage 当前类的用途描述
 */
public class CustomEnvironmentEncryptProcessor implements EnvironmentPostProcessor {
    
    Map<String,String> map = new HashMap<>();
    OriginTrackedMapPropertySource properties = null;
    {
        map.put("mysql.desc", "hello world");
        properties = new OriginTrackedMapPropertySource("dd", map);
    }

    /**
     * 可以在此处将自己解密后的properties信息放入到Environment对象中
     * @param environment 配置环境信息
     * @param application app
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("自定义的配置策略");
        environment.getPropertySources().addLast(properties);
    }
    
    
    
}
