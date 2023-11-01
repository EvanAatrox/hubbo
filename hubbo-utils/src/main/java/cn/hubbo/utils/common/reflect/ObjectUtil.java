package cn.hubbo.utils.common.reflect;

import cn.hubbo.utils.common.base.CollectionUtils;
import cn.hubbo.utils.common.base.FieldObject;
import cn.hubbo.utils.common.base.StrUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author 张晓华
 * @date 2023-10-20 10:42
 * @usage 一个反射相关的工具类
 */
@SuppressWarnings({"rawtypes", "uncheck"})
public class ObjectUtil {


    /* set前缀 */
    public static final String SET_PREFIX = "set";

    /* get前缀 */
    public static final String GET_PREFIX = "get";

    /* 对Class的缓存， */
    private static final Map<Class, List<FieldObject>> CACHE = new ConcurrentHashMap<>(60, 0.6F);


    /**
     * 要求必须存在属性对应标准的get set函数
     *
     * @param cla 目标Class
     * @return 该类中的所有属性，不包含父类的属性
     */
    @SuppressWarnings({"all"})
    public static List<String> keys(Class cla, Object target) {
        if (CACHE.containsKey(cla)) {
            return CACHE.get(cla).stream()
                    .map(fieldObject -> fieldObject.getField().getName())
                    .collect(Collectors.toList());
        }
        Field[] fields = cla.getDeclaredFields();
        List<String> names = new ArrayList<>();
        List<FieldObject> fieldObjects = new ArrayList<>();
        Arrays.stream(fields).forEach((field) -> {
            String fieldName = field.getName();
            names.add(fieldName);
            try {
                Method getter = cla.getDeclaredMethod(GET_PREFIX + StrUtils.upper(fieldName, 0));
                Method setter = cla.getDeclaredMethod(SET_PREFIX + StrUtils.upper(fieldName, 0), field.getType());
                fieldObjects.add(new FieldObject(target, field, setter, getter));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
        CACHE.put(cla, fieldObjects);
        return names;
    }

    /**
     * @param target    目标对象
     * @param fieldName 属性名
     * @param <V>       期望的类型
     * @return 获取对象指定属性的值
     */
    @SuppressWarnings({"all"})
    public static <V> V getFieldValue(Object target, String fieldName) {
        if (Objects.isNull(target)) {
            return null;
        }
        Class cla = target.getClass();
        if (CACHE.containsKey(cla)) {
            return (V) CACHE.get(cla).stream().filter(fieldObject -> fieldObject.getField().getName().equalsIgnoreCase(fieldName))
                    .findFirst()
                    .map(fieldObject -> {
                        fieldObject.setAccessible();
                        fieldObject.setTarget(target);
                        return fieldObject.getValue();
                    }).orElseGet(() -> null);
        }
        List<String> keys = keys(cla, target);
        if (keys.contains(fieldName)) {
            return getFieldValue(target, fieldName);
        }
        return null;
    }

    /**
     * 属性对拷，等同于 BeanUtils.copyProperties
     *
     * @param src  源对象
     * @param dest 目标对象
     */
    public static void copyFieldValue(Object src, Object dest) {
        Objects.requireNonNull(src, "拷贝的源对象不能为空");
        Objects.requireNonNull(dest, "拷贝的目标对象不能为空");
        if (!CACHE.containsKey(src.getClass())) {
            keys(src.getClass(), src);
        }
        if (!CACHE.containsKey(dest.getClass())) {
            keys(dest.getClass(), dest);
        }
        List<FieldObject> sourceFieldObjects = CACHE.get(src.getClass());
        List<FieldObject> destFieldObjects = CACHE.get(dest.getClass());
        Collection<FieldObject> fieldObjects = CollectionUtils.intersection(sourceFieldObjects, destFieldObjects);
        Object tmp = null;
        for (FieldObject object : fieldObjects) {
            object.setTarget(src);
            tmp = object.getValue();
            object.setTarget(dest);
            object.setValue(tmp);
        }
    }

    /**
     * @param cla 目标Class
     * @return 获取Class所有的属性
     */
    public List<String> keys(Class cla) {
        return keys(cla, null);
    }


}
