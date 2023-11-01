package cn.hubbo.utils.common.json;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张晓华
 * @date 2023-10-31 17:35
 * @usage 当前类的用途描述
 */
@SuppressWarnings({"all"})
public class BasicEnumTypeAdapterFactory implements TypeAdapterFactory {


    private Map<Class, BasicEnumTypeAdapter> cache = new HashMap<>();

    /**
     * Returns a type adapter for {@code type}, or null if this factory doesn't
     * support {@code type}.  获取枚举的序列化与反序列化的适配器
     *
     * @param gson
     * @param type 序列化时对象的类型
     */
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        return cache(type);
    }

    /**
     * @param type 序列化对象的类型
     * @param <T>  期望类型
     *
     * @return 适配器对象
     */
    private <T> TypeAdapter<T> cache(TypeToken<T> type) {
        Class<? super T> targetType = type.getRawType();
        if (cache.containsKey(targetType)) {
            return cache.get(targetType);
        } else {
            if (targetType.isEnum()) {
                Class[] interfaces = targetType.getInterfaces();
                for (Class interfaceType : interfaces) {
                    if (interfaceType.equals(BasicEnum.class)) {
                        BasicEnumTypeAdapter typeAdapter = new BasicEnumTypeAdapter(type);
                        cache.put(targetType, typeAdapter);
                        return typeAdapter;
                    }
                }
            }
            // 不是枚举或者是不支持的枚举类型直接返回空即可
            cache.put(targetType, null);
        }
        return null;
    }

}
