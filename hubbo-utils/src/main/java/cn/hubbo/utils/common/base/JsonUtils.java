package cn.hubbo.utils.common.base;

import cn.hubbo.utils.common.annotation.json.Ignore;
import cn.hubbo.utils.common.json.BasicEnumTypeAdapterFactory;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张晓华
 * @date 2023-10-19 09:28
 * @usage 当前类的用途描述
 */
public class JsonUtils {


    private static final Gson DEFAULT_GSON;

    private static final Gson STRATEGIES_GSON;


    private static final Map<FieldAttributes, Boolean> cache;

    static {
        DEFAULT_GSON = createGson(null);
        STRATEGIES_GSON = createGson(getExclusionStrategies());
        cache = new ConcurrentHashMap<>();
    }

    private static Gson createGson(List<ExclusionStrategy> exclusionStrategies) {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setPrettyPrinting()
                .disableInnerClassSerialization()
                .serializeNulls()
                .setExclusionStrategies(exclusionStrategies == null ? new ExclusionStrategy[]{} : exclusionStrategies.toArray(new ExclusionStrategy[]{}))
                .registerTypeAdapterFactory(new BasicEnumTypeAdapterFactory())
                .create();
    }


    /**
     * @return 只进行了格式化设置的gson对象
     */
    public static Gson getDefaultGson() {
        return DEFAULT_GSON;
    }

    /**
     * @return 指定的排除属性策略的Gson对象
     */
    public static Gson getStrategiesGson() {
        return STRATEGIES_GSON;
    }


    /**
     * @return 属性排除策略
     */
    private static List<ExclusionStrategy> getExclusionStrategies() {
        ArrayList<ExclusionStrategy> exclusionStrategies = new ArrayList<>();
        exclusionStrategies.add(new ExclusionStrategy() {

            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                if (!cache.containsKey(f)) {
                    Ignore annotation = f.getAnnotation(Ignore.class);
                    cache.put(f, annotation != null);
                }
                return cache.get(f);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        return exclusionStrategies;
    }


}
