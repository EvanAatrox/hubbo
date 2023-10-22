package cn.hubbo.unit;

import cn.hubbo.domain.dos.User;
import cn.hubbo.utils.common.JsonUtils;
import cn.hubbo.utils.security.JWTUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.unit
 * @date 2023/10/18 22:05
 * @Copyright © 2016-2017 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public class UtilsUnitTest {


    @Test
    public void testGenerateToken() {
        User user = new User().setUsername("user1");
        String token = JWTUtils.generateToken(UUID.randomUUID().toString(), JsonUtils.getDefaultGson().toJson(user));
        System.out.println(token);
    }

}
