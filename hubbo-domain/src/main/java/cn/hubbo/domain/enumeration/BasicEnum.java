package cn.hubbo.domain.enumeration;

/**
 * @author 张晓华
 * @date 2023-10-31 17:18
 * @usage 当前类的用途描述
 */
public interface BasicEnum<T> {
    
    /* 枚举的值 */
    T getCode();
    
    /* 枚举的基本描述信息 */
    String getDesc();
    
    
}
