package cn.hubbo.utils.reflect;

import sun.misc.Unsafe;

/**
 * @author 张晓华
 * @date 2023-10-19 11:18
 * @usage 一个反射相关的工具类
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ReflectUtils {
    
    
    private static final Unsafe UNSAFE;
    
    static {
        UNSAFE = UnSafeUtils.getUnSafeInstance();
    }

    /**
     * @return 获取unsafe操作对象
     */
    public static Unsafe getUnsafeInstance() {
        return UNSAFE;
    }


    /**
     * 绕过反射创建对象
     * @param targetClass 目标Class对象
     * @return 指定类的实例对象
     * @param <T> 期望的类型
     */
    public static<T> T createObjectInstance(Class targetClass) {
        Unsafe unsafe = getUnsafeInstance();
        try {
            return (T) unsafe.allocateInstance(targetClass);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    
    
    
}
