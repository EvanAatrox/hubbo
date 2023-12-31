package cn.hubbo.application;

import cn.hubbo.utils.common.annotation.test.TestCase;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author 张晓华
 * @date 2023-10-19 14:20
 * @usage 当前类的用途描述
 */
@SpringBootTest
public class SystemConfigurationTest {

    @Resource
    private Environment environment;


    @Test
    @TestCase("Vault加密配置读取测试")
    public void testReadApplicationConfig() {
        String password = environment.getProperty("mysql.password");
        String testContent = environment.getProperty("mysql.desc");
        System.out.println(password);
        System.out.println(testContent);
    }


    @Test
    @TestCase("Consul测试")
    public void testReadConfig() {
        String code = environment.getProperty("spring.datasource.driver-class-name");
        System.out.println(code);
        try {
            Class<?> cla = Class.forName(code);
            System.out.println(cla.getSimpleName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String password = environment.getProperty("spring.data.redis.password");
        System.out.println(password);
    }

    @Test
    @TestCase("配置信息读取")
    public void readConfigurationAtSino() {
        String content = environment.getProperty("spring.cloud.consul.host");
        String url = environment.getProperty("spring.datasource.url");
        String flag = environment.getProperty("spring.main.allow-circular-references");
        String remark = environment.getProperty("test.content");
        String uniqueServiceConfig = environment.getProperty("hubbo.test");
        System.out.println(content);
        System.out.println(url);
        System.out.println(flag);
        System.out.println(remark);
        System.out.println(uniqueServiceConfig);
    }

    @Test
    @TestCase("读取sino服务的公共配置信息")
    public void testReadSinoConfigInfo() {
        String commonInfo = environment.getProperty("application.common");
        String testContent = environment.getProperty("hubbo.test");
        System.out.println(commonInfo);
        System.out.println(testContent);
    }


}
