package cn.hubbo.utils.common.reflect;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author 张晓华
 * @date 2023-10-19 11:15
 * @usage 当前类的用途描述
 */
final class UnSafeUtils {
    static Unsafe getUnSafeInstance() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
