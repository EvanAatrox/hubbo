package cn.hubbo.common.jwt;

import java.time.Duration;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.security
 * @date 2023/10/26 0:13
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public class JWTObject {

    /* token唯一标识 */
    private final String uuid;


    /* 解析出的用户名 */
    private final String username;


    /* 是否合法的token */
    private final boolean legal;


    /* 加密密钥 */
    private final String secretKey;


    /* token的有效期 */
    private final Duration duration;


    public JWTObject(String uuid, String username, boolean legal, String secretKey, Duration duration) {
        this.uuid = uuid;
        this.username = username;
        this.legal = legal;
        this.secretKey = secretKey;
        this.duration = duration;
    }


}
