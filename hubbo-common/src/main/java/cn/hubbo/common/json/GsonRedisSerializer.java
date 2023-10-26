package cn.hubbo.common.json;

import cn.hubbo.utils.common.JsonUtils;
import cn.hubbo.utils.reflect.ReflectUtils;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.common.json
 * @date 2023/10/25 22:55
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public class GsonRedisSerializer<T> implements RedisSerializer<T> {

    @SuppressWarnings({"rawtypes"})
    private final Class cla;

    private final byte[] arr = new byte[0];

    public GsonRedisSerializer(Object target) {
        this.cla = target.getClass();
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (Objects.isNull(t)) {
            return arr;
        }
        return JsonUtils.getStrategiesGson().toJson(t, cla).getBytes(StandardCharsets.UTF_8);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return null;
        }
        // TODO 反序列化错误待解决
        LinkedTreeMap treeMap = JsonUtils.getStrategiesGson().fromJson(new String(bytes, StandardCharsets.UTF_8), LinkedTreeMap.class);
        Object obj = null;
        try {
            obj = ReflectUtils.getUnsafeInstance().allocateInstance(cla);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
        BeanMap map = BeanMap.create(obj);
        map.putAll(treeMap);
        return (T) map;
    }

}
