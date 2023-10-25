package cn.hubbo.common.constant;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.common.constant
 * @date 2023/10/25 23:18
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public final class RedisConstants {


    /* 有效的token缓存 */
    public static final String USER_TOKEN_CACHE_PREFIX = "user:token:";


    /* 用户token信息前缀 */
    public static final String USER_TOKEN_PREFIX = "user:auth:token:";


    /* 根据用户名检索用户信息时加一层缓存,避免大量的请求去查询数据库 */
    public static final String USER_SEARCH_USERNAME_PREFIX = "user:search:username:";


}
