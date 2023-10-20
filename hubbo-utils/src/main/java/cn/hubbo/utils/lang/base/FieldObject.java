package cn.hubbo.utils.lang.base;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * @author 张晓华
 * @date 2023-10-20 10:47
 * @usage 当前类的用途描述
 */
public class FieldObject {

    /* field属性 */
    private final Field field;
    /* filed的set方法 */
    private final Method setter;
    /* 属性的getter */
    private final Method getter;
    /* 对象 */
    private Object target;

    public FieldObject(Object target, Field field, Method setter, Method getter) {
        this.target = target;
        this.field = field;
        this.setter = setter;
        this.getter = getter;
    }

    public Field getField() {
        return field;
    }

    public Method getSetter() {
        return setter;
    }

    public Method getGetter() {
        return getter;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    /**
     * 修改属性的权限修饰符
     *
     * @param consumer 该函数可以接收到当前对象，对权限修饰符进行修改操作
     */
    public void changeModifiers(Consumer<FieldObject> consumer) {
        consumer.accept(this);
    }

    public void setAccessible() {
        this.field.setAccessible(true);
    }

    @SuppressWarnings({"all"})
    public <V> V getValue() {
        try {
            return (V) this.getGetter().invoke(this.target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(Object value) {
        try {
            this.setter.invoke(this.target, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
