package cn.hubbo.common.json;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author 张晓华
 * @date 2023-10-31 17:23
 * @usage 当前类的用途描述
 */
public class BasicEnumTypeAdapter<T extends BasicEnumTypeAdapter> extends TypeAdapter<T> {


    public BasicEnumTypeAdapter(TypeToken<T> typeToken) {
        

    }


    /**
     * Writes one JSON value (an array, object, string, number, boolean or null)
     * for {@code value}.
     *
     * @param out
     * @param value the Java object to write. May be null.
     */
    @Override
    public void write(JsonWriter out, T value) throws IOException {

    }

    /**
     * Reads one JSON value (an array, object, string, number, boolean or null)
     * and converts it to a Java object. Returns the converted object.
     *
     * @param in
     *
     * @return the converted Java object. May be null.
     */
    @Override
    public T read(JsonReader in) throws IOException {
        return null;
    }
}
