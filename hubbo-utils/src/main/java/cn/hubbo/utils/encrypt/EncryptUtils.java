package cn.hubbo.utils.encrypt;

import cn.hubbo.utils.common.base.StrUtils;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.utils.encrypt
 * @date 2023/11/8 23:46
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public class EncryptUtils {


    /* RSA算法 */
    public static final String ALGORITHM = "RSA";

    /*  */
    public static final String SIGN_ALGORITHM = "SHA512withRSA";


    /* RSA最大加密明文大小 */
    public static final int MAX_ENCRYPT_BLOCK = 117;


    /* RSA最大解密明文大小 */
    public static final int MAX_DECRYPT_BLOCK = 128;


    /* 默认种子, 构建RSA密钥对, 生成的密钥对不变 */
    public static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";


    /* 生成的RSA key大小 */
    public static final int KEY_SIZE = 1024;


    /* AES算法 */
    public static final String AES_ALGORITHM = "AES";


    /**
     * 加密模式之 ECB，算法/模式/补码方式
     */
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";


    /**
     * 加密模式之 CBC，算法/模式/补码方式
     */
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";


    /**
     * 加密模式之 CFB，算法/模式/补码方式
     */
    private static final String AES_CFB = "AES/CFB/PKCS5Padding";


    /**
     * AES 中的 IV 必须是 16 字节（128位）长
     */
    private static final Integer IV_LENGTH = 16;


    /**
     * @param content 文本字符串
     * @return 计算文本字符串的MD5值
     */
    public static String md5(String content) {
        try {
            assert StringUtils.isNotBlank(content) : "md5函数输入源 content文本不允许为空";
            return md5(StrUtils.ifNil(content).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param bytes 字节数组
     * @return 对字节数组进行MD5运算
     */
    public static String md5(byte[] bytes) {
        assert bytes != null && bytes.length > 0 : "md5函数输入源 bytes数组不允许为空";
        return DigestUtils.md5Hex(ArrayUtil.isEmpty(bytes) ? new byte[]{} : bytes);
    }


    /**
     * @param inputStream 文件输入流
     * @return 计算文件的md5值
     */
    public static String md5(InputStream inputStream) {
        assert inputStream != null : "输入流不允许为空";
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[8196];
            int len = 0;
            while ((len = bufferedInputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
            bytes = null;
            byte[] array = byteArrayOutputStream.toByteArray();
            inputStream.close();
            bufferedInputStream.close();
            byteArrayOutputStream.close();
            return md5(array);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @return 生成RSA 密钥,key为公钥,value为私钥
     */
    public static Pair<String, String> generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return Pair.of(Base64.encodeBase64String(keyPair.getPublic().getEncoded()), Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用公钥加密文本信息
     *
     * @param publicKeyString 公钥字符串
     * @param rawContent      原始文本信息
     * @return 加密后的文本信息
     */
    public static String encryptWithPublicKey(String publicKeyString, String rawContent) {
        assert StringUtils.isNotBlank(publicKeyString) && StringUtils.isNotBlank(rawContent) : "RSA加密所需的参数不允许为空";
        try {
            PublicKey publicKey = getPublicKey(publicKeyString);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64String(doFinal(cipher, rawContent.getBytes(StandardCharsets.UTF_8), Mode.ENCRYPT));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param privateKeyString 私钥字符串
     * @param encodingContent  被公钥加密过的文本信息
     * @return 返回原始文本信息
     */
    public static String decryptWithPrivateKey(String privateKeyString, String encodingContent) {
        assert StringUtils.isNotBlank(privateKeyString) && StringUtils.isNotBlank(encodingContent) : "RSA解密所需的参数不允许为空";
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyString);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = doFinal(cipher, Base64.decodeBase64(encodingContent), Mode.DECRYPT);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param privateKeyString 私钥字符串
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String privateKeyString) {
        assert StringUtils.isNotBlank(privateKeyString) : "RSA私钥不允许为空";
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param publicKeyString 公钥字符串
     * @return 公钥
     */
    public static PublicKey getPublicKey(String publicKeyString) {
        assert StringUtils.isNotBlank(publicKeyString) : "RSA公钥不允许为空";
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param cipher 加密器
     * @param bytes  需要加密或者解密的数据
     * @param mode   解密或者加密
     * @return 解密或者加密后的数据
     */
    private static byte[] doFinal(Cipher cipher, byte[] bytes, Mode mode) {
        try {
            var times = 0;
            var len = bytes.length;
            var offset = 0;
            var cache = new byte[0];
            var size = switch (mode) {
                case ENCRYPT -> MAX_ENCRYPT_BLOCK;
                case DECRYPT -> MAX_DECRYPT_BLOCK;
            };
            var byteArrayOutputStream = new ByteArrayOutputStream();
            while (len - offset > 0) {
                if (len - offset > size) {
                    // 每次读取size大小的数据
                    cache = cipher.doFinal(bytes, offset, size);
                } else {
                    // 读取完剩余的所有数据
                    cache = cipher.doFinal(bytes, offset, len - offset);
                }
                byteArrayOutputStream.write(cache);
                times++;
                offset = times * size;
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 签名
     *
     * @param privateKeyString 私钥字符串
     * @param rawContent       原始文本
     * @return 对原始文本的签名信息
     */
    public static String sign(String privateKeyString, String rawContent) {
        assert StringUtils.isNotBlank(privateKeyString) && StringUtils.isNotBlank(rawContent) : "RSA签名的参数不允许为空";
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyString);
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(StrUtils.ifNil(rawContent).getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 验签
     *
     * @param publicKeyString 公钥字符串
     * @param rawContent      原始文本内容
     * @param sign            签名信息
     * @return 数据签名是否一致
     */
    public static boolean verify(String publicKeyString, String rawContent, String sign) {
        assert StringUtils.isNotBlank(publicKeyString) && StringUtils.isNotBlank(rawContent) && StringUtils.isNotBlank(sign) : "RSA验签的参数不允许为空";
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            signature.initVerify(getPublicKey(publicKeyString));
            signature.update(StrUtils.ifNil(rawContent).getBytes(StandardCharsets.UTF_8));
            return signature.verify(Base64.decodeBase64(StrUtils.ifNil(sign)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param bytes 字节数组
     * @return 字节数组的16进制形式
     */
    public static String toHexString(byte[] bytes) {
        assert bytes != null && bytes.length > 0 : "bytes数组无效";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if (i == bytes.length - 1) {
                sb.append(Integer.toHexString(bytes[i] & 0xff));
            } else {
                sb.append(Integer.toHexString(bytes[i] & 0xff)).append("-");
            }
        }
        return sb.toString().toUpperCase();
    }


    /**
     * @param hexString 16进制字符串
     * @return 16进制字符串对应的二进制数据
     */
    public static byte[] toBinaryBytes(String hexString) {
        assert StringUtils.isNotBlank(hexString) : "无效的输入数据";
        String[] split = hexString.split("-");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < split.length; i++) {
            byteArrayOutputStream.write(Integer.parseInt(split[i], 10));
        }
        return byteArrayOutputStream.toByteArray();
    }


    /**
     * 生成AES密钥
     *
     * @return AES密钥
     */
    public static String generateAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            byte[] bytes = RandomUtils.getRandomBytes(16);
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.encodeBase64String(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取AES密钥
     *
     * @param secretString 密钥字符串
     * @return 密钥
     */
    public static SecretKeySpec getAESKey(String secretString) {
        assert StringUtils.isNotBlank(secretString) : "AES密钥不允许为空";
        return new SecretKeySpec(Base64.decodeBase64(secretString), AES_ALGORITHM);
    }


    /**
     * 加密
     *
     * @param rawContent 原始文本内容
     * @param secret     AES密钥
     * @return 加密后的文本
     */
    public static String encryptWithAESKey(final String rawContent, String secret) {
        assert StringUtils.isNotBlank(rawContent) && StringUtils.isNotBlank(secret) : "AES加密参数不允许为空";
        return Base64.encodeBase64String(AES(Mode.ENCRYPT, rawContent.getBytes(), secret, null));
    }


    /**
     * 解密
     *
     * @param encodingContent 加密后的文本信息
     * @param secret          AES密钥
     * @return 原始文本
     */
    public static String decryptWithAESKey(String encodingContent, String secret) {
        assert StringUtils.isNotBlank(encodingContent) && StringUtils.isNotBlank(secret) : "AES解密参数不允许为空";
        return new String(AES(Mode.DECRYPT, Base64.decodeBase64(encodingContent), secret, null));
    }

    /**
     * @param mode    模式(加密/解密)
     * @param content 文本信息
     * @param secret  密钥
     * @param iv      AES偏移量
     * @return 加密或者解密后的文本信息
     */
    public static byte[] AES(Mode mode, byte[] content, String secret, String iv) {
        try {
            String algorithm = StringUtils.isBlank(iv) ? AES_ALGORITHM : AES_CBC;
            Cipher cipher = Cipher.getInstance(algorithm);
            var modeValue = -1;
            if (mode.equals(Mode.ENCRYPT)) {
                modeValue = Cipher.ENCRYPT_MODE;
            } else {
                modeValue = Cipher.DECRYPT_MODE;
            }
            if (StringUtils.isNotBlank(iv)) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.decodeBase64(iv));
                cipher.init(modeValue, getAESKey(secret), ivParameterSpec);
            } else {
                cipher.init(modeValue, getAESKey(secret));
            }
            return cipher.doFinal(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * CBC模式下加密
     *
     * @param rawContent 原始文本信息
     * @param secret     密钥
     * @param iv         偏移量
     * @return CBC模式的加密文本
     */
    public static String encryptWithAESKey(final String rawContent, String secret, String iv) {
        assert StringUtils.isNotBlank(rawContent) && StringUtils.isNotBlank(secret) && StringUtils.isNotBlank(iv) : "AES加密参数不允许为空";
        return Base64.encodeBase64String(AES(Mode.ENCRYPT, rawContent.getBytes(), secret, iv));
    }


    /**
     * CBC模式解密
     *
     * @param encodingContent 加密后的文本信息
     * @param secret          AES密钥
     * @param iv              偏移量
     * @return 原始文本
     */
    public static String decryptWithAESKey(String encodingContent, String secret, String iv) {
        assert StringUtils.isNotBlank(encodingContent) && StringUtils.isNotBlank(secret) && StringUtils.isNotBlank(iv) : "AES解密参数不允许为空";
        return new String(AES(Mode.DECRYPT, Base64.decodeBase64(encodingContent), secret, iv));
    }


    public enum Mode {
        /* 加密模式 */
        ENCRYPT,
        /* 解密模式 */
        DECRYPT,
    }


}
