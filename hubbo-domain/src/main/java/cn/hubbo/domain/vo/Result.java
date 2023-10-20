package cn.hubbo.domain.vo;

import cn.hubbo.domain.dos.SystemResponseCode;
import cn.hubbo.domain.enumeration.ResponseStatusEnum;
import lombok.Data;

/**
 * @author 张晓华
 * @date 2023-10-20 09:03
 * @usage 当前类的用途描述
 */
@Data
public class Result {

    /* 系统状态码 */
    private Integer code;

    /* 描述信息 */
    private String msg;

    public Result(SystemResponseCode responseCode) {
        this(responseCode.getCode(), responseCode.getMsg());
    }

    public Result(ResponseStatusEnum responseStatusEnum) {
        this(responseStatusEnum.getCode(), responseStatusEnum.getMsg());
    }

    private Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
