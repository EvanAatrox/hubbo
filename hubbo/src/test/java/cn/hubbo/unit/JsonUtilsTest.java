package cn.hubbo.unit;

import cn.hubbo.domain.dos.User;
import cn.hubbo.domain.enumeration.GenderEnum;
import cn.hubbo.domain.enumeration.AccountStatusEnum;
import cn.hubbo.utils.annotation.test.TestCase;
import cn.hubbo.utils.common.JsonUtils;
import cn.hubbo.utils.date.TimeUtils;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

/**
 * @author 张晓华
 * @date 2023-10-19 09:54
 * @usage 当前类的用途描述
 */
public class JsonUtilsTest {


    @Test
    @TestCase("Gson && 自定义的注解测试")
    public void testStrategiesSerialize() {
        User user = new User()
                .setUserId(100)
                .setUsername("test")
                .setPassword("123456")
                .setGender(GenderEnum.MALE)
                .setPhone("12345678901")
                .setAccountStatus(AccountStatusEnum.DEFAULT);
        Gson defaultGson = JsonUtils.getStrategiesGson();
        Long time1 = TimeUtils.execute(() -> {
            String str1 = defaultGson.toJson(user);
        }, 1000);
        Gson strategiesGson = JsonUtils.getDefaultGson();
        Long time2 = TimeUtils.execute(() -> {
            String str2 = strategiesGson.toJson(user);
        }, 1000);
        System.out.println("time1 " + time1);
        System.out.println("time2 " + time2);
    }


}
