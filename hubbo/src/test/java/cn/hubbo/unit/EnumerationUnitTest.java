package cn.hubbo.unit;

import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.AccountStatusEnum;
import cn.hubbo.domain.enumeration.GenderEnum;
import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import cn.hubbo.utils.annotation.test.TestCase;
import cn.hubbo.utils.common.JsonUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

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


    @Test
    @TestCase("测试反序列化枚举字段信息")
    public void testGsonSerializer() {
        User user = new User()
                .setUserId(100)
                .setUsername("test")
                .setGender(GenderEnum.MALE)
                .setAccountStatus(AccountStatusEnum.DEFAULT)
                .setRegisterDate(new Date())
                .setRemark("测试用户")
                .setEmail("javago0309@163.com")
                .setPhone("19937656***");
        String jsonStr = JsonUtils.getStrategiesGson().toJson(user);
        System.out.println(jsonStr);
        User userCopy = JsonUtils.getStrategiesGson().fromJson(jsonStr, User.class);
        userCopy = JsonUtils.getStrategiesGson().fromJson(jsonStr, User.class);
        System.out.println(userCopy);

    }


}
