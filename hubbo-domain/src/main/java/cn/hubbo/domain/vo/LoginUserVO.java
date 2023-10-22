package cn.hubbo.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.domain.vo
 * @date 2023/10/22 21:30
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Data
@Accessors(chain = true)
public class LoginUserVO {

    /* 登录名,暂时只使用用户名,后面考虑加入手机号,邮箱... */
    private String username;


    /* 客户端提交过来的明文密码 */
    private String rawPasswd;


    // TODO 下面的这些属性是后期扩展要使用的

    /* 客户端提交过来的验证码 */
    private String captcha;


    /* 防重复提交的唯一标识 */
    private String token;


    /* 客户端携带的请求时间的时间戳 */
    private Date timestamp;


    /* refer来源 */
    private String refer;


    /* 客户端的ip地址 */
    private String host;


}
