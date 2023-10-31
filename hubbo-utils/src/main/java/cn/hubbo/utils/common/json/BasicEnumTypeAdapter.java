package cn.hubbo.utils.common.json;

import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author 张晓华
 * @date 2023-10-31 17:23
 * @usage 枚举对象序列化与反序列的转换器类
 */
@SuppressWarnings({"all"})
public class BasicEnumTypeAdapter<T extends BasicEnum> extends TypeAdapter<T> {


    private final EnumerationType enumerationType;

    private final T[] allEnumElements;

    /**
     * @param typeToken 需要序列化的枚举对象类型
     */
    public BasicEnumTypeAdapter(TypeToken<T> typeToken) {
        // 当前枚举的类型
        final Class<T> enumType = (Class<T>) typeToken.getRawType();
        // BasicEnum code的类型
        Class<T> codeValueType = null;
        Type[] interfaces = enumType.getGenericInterfaces();
        for (Type interfaceType : interfaces) {
            if (interfaceType instanceof ParameterizedType parameterizedType) {
                Class type = (Class) parameterizedType.getRawType();
                if (type.equals(BasicEnum.class)) {
                    Type[] arguments = parameterizedType.getActualTypeArguments();
                    codeValueType = (Class) arguments[0];
                }
            }
        }
        if (codeValueType == null) {
            throw new IllegalArgumentException("未获取到枚举类型");
        } else if (codeValueType.equals(Integer.class)) {
            enumerationType = EnumerationType.INTEGER;
        } else if (codeValueType.equals(String.class)) {
            enumerationType = EnumerationType.STRING;
        } else {
            throw new IllegalArgumentException("不支持的枚举类型");
        }
        allEnumElements = enumType.getEnumConstants();
    }


    /**
     * Writes one JSON value (an array, object, string, number, boolean or null)
     * for {@code value}.
     *
     * @param out
     * @param value the Java object to write. May be null.
     */
    @Override
    public void write(JsonWriter out, BasicEnum value) throws IOException {
        switch (enumerationType) {
            case STRING -> out.beginObject()
                    .name("code").value((String) value.getCode())
                    .name("desc").value(value.getDesc())
                    .endObject();
            case INTEGER -> out.beginObject()
                    .name("code").value((Integer) value.getCode())
                    .name("desc").value(value.getDesc())
                    .endObject();
            default -> throw new IllegalArgumentException(String.format("不支持的枚举格式 %s", enumerationType));
        }
    }

    /**
     * Reads one JSON value (an array, object, string, number, boolean or null)
     * and converts it to a Java object. Returns the converted object.
     *
     * @param in 序列化后的json字符串信息
     * @return the converted Java object. May be null. 反序列化后的枚举对象
     */
    @Override
    public T read(JsonReader in) throws IOException {
        JsonObject jsonObject = Streams.parse(in).getAsJsonObject();
        return switch (enumerationType) {
            case STRING -> {
                String code = jsonObject.get("code").getAsString();
                yield cache(code);
            }
            case INTEGER -> {
                int code = jsonObject.get("code").getAsInt();
                yield cache(code);
            }
            default -> throw new IllegalArgumentException(String.format("不支持的枚举格式 %s", enumerationType));
        };
    }

    private T cache(Object code) {
        return Arrays.stream(allEnumElements)
                .filter(obj -> obj.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("不存在的枚举对象"));
    }

    private enum EnumerationType {
        STRING, INTEGER;
    }

}
