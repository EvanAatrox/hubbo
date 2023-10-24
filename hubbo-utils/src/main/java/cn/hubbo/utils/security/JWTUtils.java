package cn.hubbo.utils.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.security
 * @date 2023/10/18 21:51
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public final class JWTUtils {

    private static final Algorithm ALGORITHM = Algorithm.HMAC256("123456");

    private static final String ISSUER_INFO = "https://hubbo.cn";

    private static final Map<String, Object> HEADER = new HashMap<>();

    static {
        HEADER.put("alg", "HS256");
        HEADER.put("typ", "JWT");
    }

    /**
     * @param id        唯一ID,后期token续期可能会用到
     * @param claimInfo payLoad载荷信息
     * @return token
     */
    public static String generateToken(String id, String username) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return JWT.create()
                .withIssuer(ISSUER_INFO)
                .withJWTId(id)
                .withSubject("哈勃")
                .withClaim("info", username)
                .withIssuedAt(new Date())
                .withExpiresAt(calendar.toInstant())
                .withHeader(HEADER)
                .sign(ALGORITHM);
    }


    /**
     * @param token token信息
     * @param cla   期望的目标Class
     * @param <T>   期望的类型
     * @return 将token中的信息解析成对象
     */
    @SuppressWarnings({"all"})
    public static <T> T verifyToken(String token, Class cla) {
        try {
            DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(token);
            String payload = decodedJWT.getPayload();
            //return (T) JsonUtils.getDefaultGson().fromJson("", cla);
            String info = String.valueOf(decodedJWT.getClaim("info"));
            if (info.contains("\"")) {
                info = info.replaceAll("\"", "");
            }
            return (T) info;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
