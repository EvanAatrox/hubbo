package cn.hubbo.common.json;

import cn.hubbo.domain.enumeration.BasicEnum;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * @author 张晓华
 * @date 2023-10-31 17:35
 * @usage 当前类的用途描述
 */
@SuppressWarnings({"all"})
public class BasicEnumTypeAdapterFactory implements TypeAdapterFactory {


    /**
     * Returns a type adapter for {@code type}, or null if this factory doesn't
     * support {@code type}.
     *
     * @param gson
     * @param type
     */
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }
        Class[] interfaces = rawType.getInterfaces();
        for (Class interfaceType : interfaces) {
            if (interfaceType.equals(BasicEnum.class)) {
                return new BasicEnumTypeAdapter(type);
            }
        }
        return null;
    }


}
