package cn.hubbo.application;

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
public class VaultConfigurationTest {
    
    @Resource
    private Environment environment;
    
    
    @Test
    public void testReadApplicationConfig() {
        String password = environment.getProperty("mysql.password");
        String testContent = environment.getProperty("mysql.desc");
        System.out.println(password);
        System.out.println(testContent);
    }
    
    
    
}
