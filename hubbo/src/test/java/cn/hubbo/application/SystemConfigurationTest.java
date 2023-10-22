package cn.hubbo.application;

import cn.hubbo.utils.annotation.test.TestCase;
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
    @TestCase("Vault配置读取测试")
    public void testReadApplicationConfig() {
        String password = environment.getProperty("mysql.password");
        String testContent = environment.getProperty("mysql.desc");
        System.out.println(password);
        System.out.println(testContent);
    }


    @Test
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


}
