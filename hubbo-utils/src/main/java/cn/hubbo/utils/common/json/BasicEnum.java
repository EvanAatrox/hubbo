package cn.hubbo.utils.common.json;

/**
 * @author 张晓华
 * @date 2023-10-31 17:18
 * @usage 当前系统中所有枚举类必须要实现的接口, 当做规范使用, 否则枚举类在某些情况下无法反序列化
 */
public interface BasicEnum<T> {

    /**
     * @return 枚举的值, 当前只支持String和Integer类型
     */
    T getCode();

    /**
     * @return 枚举对象的基本描述信息
     */
    String getDesc();


    /**
     * 每一个枚举对象的唯一标识,子类有需求可以重写,未重写的清空在必须保证每个枚举对象的desc信息不重复,否则会出现数据错误
     *
     * @return 枚举对象的唯一标识
     */
    default String identify() {
        return getDesc();
    }


}
