package cn.hubbo.unit;

import cn.hubbo.domain.dos.User;
import cn.hubbo.utils.annotation.test.TestCase;
import cn.hubbo.utils.date.TimeUtils;
import cn.hubbo.utils.reflect.ObjectUtil;
import cn.hubbo.utils.reflect.ReflectUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.util.List;
import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-10-19 11:17
 * @usage 当前类的用途描述
 */
public class ReflectUtilsTest {


    @Test
    @TestCase("Unsafe实例创建对象实例测试")
    public void testGetUnSafeInstance() throws InstantiationException {
        Unsafe unsafe = ReflectUtils.getUnsafeInstance();
        Objects.requireNonNull(unsafe, "未获取到unsafe实例");
        System.out.println(unsafe);
        User user = (User) unsafe.allocateInstance(User.class);
        System.out.println(user);
    }

    @RepeatedTest(value = 10)
    @TestCase("测试ObjectUtil.keys")
    public void testObjectUtilKeys() {
        Long execute = TimeUtils.execute(() -> {
            List<String> keys = ObjectUtil.keys(User.class, null);
            System.out.println(keys);
        });
        System.out.println("耗时 " + execute + " ms");
    }
    
    @Test
    @TestCase("获取指定的field的value值")
    public void testGetSpecifiedField() {
        User user = new User().setUserId(1213414);
        Object value = ObjectUtil.getFieldValue(user, "userId");
        System.out.println(value);
    }
    
    @Test
    @TestCase("测试属性拷贝")
    public void testCopyProperties() {
        User user = new User().setUserId(121231)
                .setUsername("哈哈哈")
                .setPassword("12134141");
        User test = new User();
        ObjectUtil.copyFieldValue(user,test);
        System.out.println(test);
    }


}
