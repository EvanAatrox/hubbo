package cn.hubbo.common.json;

import cn.hubbo.utils.common.JsonUtils;
import cn.hubbo.utils.common.json.GsonEntity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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


public class GsonRedisSerializer implements RedisSerializer<Object> {


    private final byte[] arr = new byte[0];


    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        if (Objects.isNull(obj)) {
            return arr;
        }
        return JsonUtils.getStrategiesGson().toJson(obj).getBytes(StandardCharsets.UTF_8);
    }
    
    
    
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return null;
        }
        String jsonStr = new String(bytes, StandardCharsets.UTF_8);
        JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();
        String className = jsonObject.get(GsonEntity.PROPERTY_NAME).getAsString();
        try {
            Class<?> cla = Class.forName(className);
            return JsonUtils.getStrategiesGson().fromJson(jsonStr, cla);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
