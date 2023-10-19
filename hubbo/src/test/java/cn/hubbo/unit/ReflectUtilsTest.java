package cn.hubbo.unit;

import cn.hubbo.domain.entity.User;
import cn.hubbo.utils.reflect.ReflectUtils;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-10-19 11:17
 * @usage 当前类的用途描述
 */
public class ReflectUtilsTest {
    
    
    @Test
    public void testGetUnSafeInstance() throws InstantiationException {
        Unsafe unsafe = ReflectUtils.getUnsafeInstance();
        Objects.requireNonNull(unsafe,"未获取到unsafe实例");
        System.out.println(unsafe);
        User user = (User) unsafe.allocateInstance(User.class);
        System.out.println(user);
    }
    
    
}
