package cn.hubbo.common.domain.to;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.security
 * @date 2023/10/26 0:13
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
@Getter
@Setter(AccessLevel.PACKAGE)
public class JWTObject {

    /* token唯一标识 */
    private String uuid;


    /* 解析出的用户名 */
    private String username;


    /* 是否合法的token */
    private boolean legal;


    /* 加密密钥 */
    private String secretKey;


    /* token的有效期 */
    private Duration duration = Duration.ofDays(1L);


    private Map<String, Object> claim = new HashMap<>();


    private Map<String, Object> header = new HashMap<>();


    private Algorithm algorithm;


    private String token;

    {
        // 初始值,实例后之后可以被自己的配置覆盖掉
        header.put("typ", "JWT");
        header.put("alg", "HS256");
    }

    JWTObject() {

    }


    public JWTObject(String uuid, String username, String secretKey, Duration duration, String algorithmName) {
        this.uuid = uuid;
        this.username = username;
        this.secretKey = secretKey;
        this.duration = duration;
        this.initialAlgorithm(algorithmName);
    }

    protected static Algorithm getAlgorithm(String algorithmName, String secretKey) {
        if (StringUtils.isBlank(algorithmName)) {
            algorithmName = "HMAC256";
        }
        switch (algorithmName) {
            case "RSA256":
                try {
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RAS");
                    keyPairGenerator.initialize(2048, new SecureRandom());
                    KeyPair keyPair = keyPairGenerator.generateKeyPair();
                    return Algorithm.RSA256((RSAPublicKey) keyPair.getPublic(), (RSAPrivateKey) keyPair.getPrivate());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            default:
                return Algorithm.HMAC256(secretKey);
        }
    }

    public void initialAlgorithm(String algorithmName) {
        this.algorithm = getAlgorithm(algorithmName, this.secretKey);
    }

    public void buildToken() {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MILLISECOND, Long.valueOf(this.duration.toMillis()).intValue());
        this.token = JWT.create()
                .withJWTId(this.uuid)
                .withClaim("username", this.username)
                .withIssuedAt(new Date())
                .withExpiresAt(instance.getTime())
                .withHeader(header)
                .sign(algorithm);
    }

    public String getToken() {
        this.buildToken();
        return this.token;
    }

    public String getUsername() {
        if (this.username.contains("\"")) {
            this.username = this.username.replace("\"", "");
        }
        return username;
    }
}
