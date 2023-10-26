package cn.hubbo.common.domain.to;

import cn.hubbo.common.domain.to.properties.JWTProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Duration;
import java.util.HashMap;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.common.utils
 * @date 2023/10/26 21:33
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public final class JWTTokenBuilder {

    private final JWTObject jwtObject;

    private JWTTokenBuilder() {
        this.jwtObject = new JWTObject();
    }

    public static JWTTokenBuilder create() {
        return new JWTTokenBuilder();
    }

    public static JWTObject parseJWTToken(String token, JWTProperties jwtProperties) {
        JWTObject object = new JWTObject();
        try {
            DecodedJWT decodedJWT = JWT.require(JWTObject.getAlgorithm(jwtProperties.getAlgorithm(), jwtProperties.getSecretKey())).build()
                    .verify(token);
            object.setLegal(true);
            object.setUuid(decodedJWT.getId());
            HashMap<String, Object> map = new HashMap<>(decodedJWT.getClaims());
            object.setClaim(map);
            object.setUsername(decodedJWT.getClaims().get("username").toString());
        } catch (Exception ignore) {
            object.setLegal(false);
        }
        return object;
    }

    public JWTTokenBuilder id(String id) {
        this.jwtObject.setUuid(id);
        return JWTTokenBuilder.this;
    }

    public JWTTokenBuilder claim(String username) {
        this.jwtObject.setUsername(username);
        return JWTTokenBuilder.this;
    }

    public JWTTokenBuilder timeout(Duration duration) {
        this.jwtObject.setDuration(duration);
        return JWTTokenBuilder.this;
    }

    public JWTTokenBuilder sign(String secretKey) {
        this.jwtObject.setSecretKey(secretKey);
        return JWTTokenBuilder.this;
    }

    public JWTObject build() {
        // 先暂时为空
        return new JWTObject(this.jwtObject.getUuid(), this.jwtObject.getUsername(), this.jwtObject.getSecretKey(), this.jwtObject.getDuration(), null);
    }

}
