package cn.hubbo.domain.enumeration;

import lombok.Getter;

/**
 * @author 张晓华
 * @date 2023-10-20 09:04
 * @usage 当前类的用途描述
 */
@Getter
public enum ResponseStatusEnum {

    SUCCESS(200, "OK"),
    ERROR(500, "Error");
    

    private final Integer code;
    private final String msg;

    ResponseStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().concat(" " + this.code);
    }

}
