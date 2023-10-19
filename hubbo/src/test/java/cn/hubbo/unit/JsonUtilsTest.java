package cn.hubbo.unit;

import cn.hubbo.domain.entity.User;
import cn.hubbo.domain.enumeration.GenderEnum;
import cn.hubbo.domain.enumeration.UserStatusEnum;
import cn.hubbo.utils.base.JsonUtils;
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
    public void testStrategiesSerialize() {
        User user = new User()
                .setUserId(100)
                .setUsername("test")
                .setPassword("123456")
                .setGender(GenderEnum.MALE)
                .setPhone("12345678901")
                .setAccountStatus(UserStatusEnum.DEFAULT);
        Gson defaultGson = JsonUtils.getStrategiesGson();
        Long time1 = TimeUtils.execute(() -> {
            String str1 = defaultGson.toJson(user);
        },1000);
        Gson strategiesGson = JsonUtils.getDefaultGson();
        Long time2 = TimeUtils.execute(()->{
           String str2 =strategiesGson.toJson(user);
        },1000);
        System.out.println("time1 " + time1);
        System.out.println("time2 " + time2);
    }
    
    
}
