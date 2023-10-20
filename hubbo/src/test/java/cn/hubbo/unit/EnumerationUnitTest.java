package cn.hubbo.unit;

import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.utils.annotation.test.TestCase;
import org.junit.jupiter.api.Test;

/**
 * @author 张晓华
 * @date 2023-10-20 09:04
 * @usage 当前类的用途描述
 */
public class EnumerationUnitTest {

    @Test
    @TestCase("枚举测试")
    public void testResponseStatusEnum() {
        for (ResponseStatusEnum statusEnum : ResponseStatusEnum.values()) {
            System.out.println(statusEnum);
        }
        System.out.println(ResponseStatusEnum.SUCCESS);
    }


}
