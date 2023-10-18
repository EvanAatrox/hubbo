package cn.hubbo.utils.base;

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
     * @return 本身或者空字符串
     */
    public static String ifNil(String str) {
        return str == null ? "" : str;
    }


}
