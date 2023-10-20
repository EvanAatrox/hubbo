package cn.hubbo.utils.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.base
 * @date 2023/10/18 21:52
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
public final class StrUtils {


    /**
     * 如果为空则返回空字符串以避免空指针
     *
     * @param str 字符串
     *
     * @return 本身或者空字符串
     */
    public static String ifNil(String str) {
        return str == null ? "" : str;
    }


    /**
     * 以字符串的形式返回指定索引位置的字符
     *
     * @param str   字符串
     * @param index 期望的索引
     *
     * @return 指定索引位置的字符
     */
    public static String index(String str, int index) {
        return ifNil(str).charAt(index) + "";
    }

    /**
     * 如果index为-1，则将整个字符串转大写，否则只是将指定的index位置的字符转成大写
     *
     * @param str   字符串
     * @param index 索引
     *
     * @return 转大写
     */
    public static String upper(String str, int index) {
        if (index == -1) {
            return ifNil(str).toUpperCase();
        }
        String letter = ifNil(index(str, index)).toUpperCase();
        return str.substring(0, index).concat(letter).concat(str.substring(index + 1));
    }


    /**
     * @param str 字符串
     *
     * @return 大写后的字符串
     */
    public static String upper(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return upper(str, -1);
    }

    
    
    
}
