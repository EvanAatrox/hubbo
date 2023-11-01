package cn.hubbo.utils.common.json;

/**
 * @author 张晓华
 * @date 2023-11-01 15:30
 * @usage 使用Gson将实体类对象信息保存到Redis中序列化与反序列化必须继承的父类，GSON反序列化不知道为什么就是有问题，只能在序列化时保存className信息
 */
public class GsonEntity {

    public static final String PROPERTY_NAME = "_class_name";
    public String _class_name;

    public GsonEntity() {
        this._class_name = this.getClass().getName();
    }


}
