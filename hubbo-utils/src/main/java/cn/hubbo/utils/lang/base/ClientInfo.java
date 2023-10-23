package cn.hubbo.utils.lang.base;

import cn.hutool.http.useragent.UserAgent;

import java.util.Date;

/**
 * @author 张晓华
 * @date 2023-10-23 10:21
 * @usage 当前类的用途描述
 */
public record ClientInfo(String ip, String url, String token, Date submitTime, String submitToken, UserAgent userAgent) {

}
