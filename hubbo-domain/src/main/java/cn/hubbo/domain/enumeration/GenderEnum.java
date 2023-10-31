package cn.hubbo.domain.enumeration;

import lombok.Getter;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.domain.enumeration
 * @date 2023/10/18 23:30
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Getter
public enum GenderEnum implements BasicEnum<Integer> {

    FEMALE(0, "女"),
    MALE(1, "男");


    private final Integer code;

    private final String desc;

    GenderEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
