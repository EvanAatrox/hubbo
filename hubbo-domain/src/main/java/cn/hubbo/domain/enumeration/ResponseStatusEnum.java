package cn.hubbo.domain.enumeration;

import lombok.Getter;

/**
 * @author 张晓华
 * @date 2023-10-20 09:04
 * @usage 当前类的用途描述
 */
@Getter
public enum ResponseStatusEnum implements BasicEnum<Integer> {

    SUCCESS(200, "OK"),
    ERROR(500, "Error");


    private final Integer code;
    private final String desc;

    ResponseStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().concat(" " + this.code);
    }

}
