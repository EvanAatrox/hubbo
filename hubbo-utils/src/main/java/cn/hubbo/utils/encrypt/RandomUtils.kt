package cn.hubbo.utils.encrypt

import java.util.*

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.encrypt
 * @date 2023/11/14 0:19
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */
object RandomUtils {


    private const val BASE_LINE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    /**
     * @param len 长度
     * @return 获取指定长度的随机字符串
     */
    @JvmStatic
    fun getRandomStr(len: Int): String {
        require(len > 0) { "非法的参数 len value $len" }
        val sb: StringBuilder = StringBuilder()
        val random = Random(System.currentTimeMillis())
        for (i in 0 until len) {
            sb.append(BASE_LINE[random.nextInt(BASE_LINE.length)])
        }
        return sb.toString()
    }


    /**
     * @param len 生成的字节数组的长度
     * @return byte[] 随机字节数组
     */
    @JvmStatic
    fun getRandomBytes(len: Int): ByteArray {
        if (len <= 0) {
            throw IllegalArgumentException("非法的参数 len value $len")
        }
        val bytes = ByteArray(len)
        val random = Random(System.currentTimeMillis())
        for (i in 0 until len) {
            random.nextBytes(bytes)
        }
        return bytes
    }


    /**
     * @param bound 边界
     * @return 随机的无符号Int整数
     */
    @JvmStatic
    fun getUnsignedRandomInteger(bound: Int = Int.MAX_VALUE): Int {
        val random = Random()
        return random.nextInt(bound)
    }


    @JvmStatic
    fun getRandomIV(): String {
        return getRandomStr(16);
    }


}
