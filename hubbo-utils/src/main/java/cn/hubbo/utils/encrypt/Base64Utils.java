package cn.hubbo.utils.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.encrypt
 * @date 2023/11/8 23:39
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public final class Base64Utils {


    /**
     * 进行Base64编码
     *
     * @param str 原始内容
     * @return Base64编码后的字符串
     */
    public static String encodeStr(String str) {
        assert StringUtils.isNotBlank(str) : "需要进行Base64编码的原始内容不允许为空";
        return Base64.encodeBase64String(str.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 进行Base64解码
     *
     * @param str Base64编码后的文本
     * @return 原始文本信息
     */
    public static String decodeStr(String str) {
        assert StringUtils.isNotBlank(str) : "传入的Base64字符串不允许为空";
        return new String(Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }


    /**
     * 对二进制数据进行Base64编码
     *
     * @param bytes 字节数组
     * @return Base64编码后的文本字符串
     */
    public static String encodeByteArray(byte[] bytes) {
        assert Objects.nonNull(bytes) && bytes.length != 0 : "需要进行Base64编码的字节数组不允许为空";
        return Base64.encodeBase64String(bytes);
    }


}
