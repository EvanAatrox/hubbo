package cn.hubbo.unit;

import cn.hubbo.utils.common.annotation.test.TestCase;
import cn.hubbo.utils.system.SystemUtils;
import org.junit.jupiter.api.Test;

/**
 * @author 张晓华
 * @date 2023-11-01 17:22
 * @usage 当前类的用途描述
 */
public class SystemUtilsUnitTest {
    
    
    
    @Test
    @TestCase("获取CPU负载信息")
    public void testCpuInfo() {
        SystemUtils.loadInfo();
    }
    
    
    
}
