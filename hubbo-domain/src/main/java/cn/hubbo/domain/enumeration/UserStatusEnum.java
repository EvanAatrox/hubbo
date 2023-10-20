package cn.hubbo.domain.enumeration;

import lombok.Getter;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.constant
 * @date 2023/10/18 23:18
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */

@Getter
public enum UserStatusEnum {

    DEFAULT(0, "正常"),
    LOCKED(1, "锁定");

    /* 状态值 */
    private final Integer code;

    /* 状态的描述信息 */
    private final String desc;


    UserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
