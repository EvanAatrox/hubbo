package cn.hubbo.utils.common.base;

import cn.hubbo.utils.common.reflect.ReflectUtils;
import cn.hubbo.utils.lang.exception.OperationNotAllowedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author 张晓华
 * @date 2023-10-20 09:54
 * @usage 当前类的用途描述
 */
public class UnModifyMap<K, V> extends HashMap<K, V> {


    @Override
    public V get(Object key) {
        V v = super.get(key);
        if (!Objects.isNull(v)) {
            Object newInstance = ReflectUtils.createObjectInstance(v.getClass());
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        execute("putAll");
    }

    @Override
    public V remove(Object key) {
        execute("remove");
        return null;
    }

    @Override
    public void clear() {
        execute("clear");
    }

    @Override
    public V putIfAbsent(K key, V value) {
        execute("putIfAbsent");
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        execute("remove");
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        execute("replace");
        return false;
    }

    @Override
    public V replace(K key, V value) {
        execute("replace");
        return null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        execute("computeIfAbsent");
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        execute("computeIfPresent");
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        execute("compute");
        return null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        execute("merge");
        return value;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        execute("replaceAll");
    }

    private void execute(String methodName) {
        String template = "method $ is not allowed to execute";
        throw new OperationNotAllowedException(template.replaceAll("\\$", methodName));
    }

}
