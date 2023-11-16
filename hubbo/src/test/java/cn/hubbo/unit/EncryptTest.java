package cn.hubbo.unit;

import cn.hubbo.utils.encrypt.Base64Utils;
import cn.hubbo.utils.encrypt.EncryptUtils;
import cn.hubbo.utils.encrypt.RandomUtils;
import cn.hutool.core.lang.Pair;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author 张晓华
 * @version V1.0
 * @Package cn.hubbo.unit
 * @date 2023/11/8 23:17
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


public class EncryptTest {


    @Test
    public void testHutoolRsa() throws IOException {
        RSA rsa = new RSA();
        String source = FileUtils.readFileToString(new File("D:\\data\\cache\\decompress\\url\\url.txt"), StandardCharsets.UTF_8);
        File file = new File("D:\\data\\cache\\decompress\\url\\public.txt");
        File dest = new File("D:\\data\\cache\\decompress\\url\\encrypt.txt");
        FileUtils.write(file, rsa.getPublicKeyBase64(), StandardCharsets.UTF_8);
        File file2 = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        FileUtils.write(file2, rsa.getPrivateKeyBase64(), StandardCharsets.UTF_8);
        byte[] encrypt = rsa.encrypt(source, StandardCharsets.UTF_8, KeyType.PublicKey);
        FileUtils.writeByteArrayToFile(dest, encrypt);
    }


    @Test
    public void testRsa2() throws IOException {
        File file = new File("D:\\data\\cache\\decompress\\url\\public.txt");
        String publicKeyString = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        File file2 = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        String privateKeyString = FileUtils.readFileToString(file2, StandardCharsets.UTF_8);
        RSA rsa = new RSA(privateKeyString, publicKeyString);
        File dest = new File("D:\\data\\cache\\decompress\\url\\encrypt.txt");
        byte[] bytes = FileUtils.readFileToByteArray(dest);
        byte[] decrypt = rsa.decrypt(bytes, KeyType.PrivateKey);
        System.out.println(new String(decrypt, StandardCharsets.UTF_8));
    }

    @Test
    public void testMd5() throws FileNotFoundException {
        String md5 = EncryptUtils.md5("abc");
        System.out.println(md5);
        String fileMd5 = EncryptUtils.md5(new FileInputStream(new File("D:\\data\\cache\\decompress\\url\\url.txt")));
        System.out.println(fileMd5);
        String mded5 = EncryptUtils.md5("abc".getBytes());
        System.out.println(mded5);
    }

    @Test
    public void testEncrypt() throws IOException {
        Pair<String, String> pair = EncryptUtils.generateKeyPair();
        var publicKey = new File("D:\\data\\cache\\decompress\\url\\public.txt");
        var privateKey = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        var file = new File("D:\\data\\cache\\decompress\\url\\url.txt");
        FileUtils.writeStringToFile(publicKey, pair.getKey());
        FileUtils.writeStringToFile(privateKey, pair.getValue());
        String encodingContent = EncryptUtils.encryptWithPublicKey(pair.getKey(), FileUtils.readFileToString(file, StandardCharsets.UTF_8));
        FileUtils.writeStringToFile(new File("D:\\data\\cache\\decompress\\url\\encrypt.txt"), encodingContent, StandardCharsets.UTF_8);
    }

    @Test
    public void testDecrypt() throws IOException {
        var encryptFile = new File("D:\\data\\cache\\decompress\\url\\encrypt.txt");
        String encodedContent = FileUtils.readFileToString(encryptFile, StandardCharsets.UTF_8);
        var privateKeyFile = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        String privateKeyString = FileUtils.readFileToString(privateKeyFile, StandardCharsets.UTF_8);
        String rawContent = EncryptUtils.decryptWithPrivateKey(privateKeyString, encodedContent);
        System.out.println(rawContent);
    }

    @Test
    public void testBase64Utils() {
        String encodedStr1 = Base64Utils.encodeStr("abc");
        String encodedStr2 = Base64.encodeBase64String("abc".getBytes(StandardCharsets.UTF_8));
        System.out.println(encodedStr1);
        System.out.println(encodedStr2);
        System.out.println(encodedStr1.equals(encodedStr2));
    }


    @Test
    public void testMd52() throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] digest = messageDigest.digest("abc".getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        System.out.println(sb);
    }


    @Test
    public void testHmacMd5() throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec("abc".getBytes(), "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte[] bytes = mac.doFinal("abc".getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        System.out.println(sb);
    }


    @Test
    public void signWithPrivate() throws IOException {
        var privateKeyFile = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        String privateKeyString = FileUtils.readFileToString(privateKeyFile, StandardCharsets.UTF_8);
        String sign = EncryptUtils.sign(privateKeyString, "abc");
        System.out.println(sign);
    }

    @Test
    public void testRadioConvert() throws IOException {
        byte[] bytes = FileUtils.readFileToByteArray(new File("D:\\data\\cache\\decompress\\url\\radio.txt"));
        String hexString = EncryptUtils.toHexString(bytes);
        System.out.println(hexString);
        byte[] bytesConvert = EncryptUtils.toBinaryBytes(hexString);
        System.out.println(Arrays.toString(bytesConvert));
    }

    @Test
    public void testVerifySign() throws IOException {
        String sign = "FMC4RTl17l1mI4FZ840lf67lCRtK6s+HcWcXQ1qluFXRkFsDLllV4HP/5H5BHZIv98F8SF+C6LYxRAgqjvPgopHzid/jcSR+CxWZmz3o7E7BLQgcbQSssNTD/flvlDK+RWQp+v5KBD0UhGC52Q5V9cz7lKi1ybU/EWa3hVgXLqQ=";
        var publicKeyFile = new File("D:\\data\\cache\\decompress\\url\\public.txt");
        String publicKeyString = FileUtils.readFileToString(publicKeyFile);
        boolean verify = EncryptUtils.verify(publicKeyString, "abc", sign);
        System.out.println(verify);
    }


    @Test
    public void testAes() throws IOException {
        String secret = EncryptUtils.generateAESKey();
        System.out.println(secret);
        var sourceContent = FileUtils.readFileToString(new File("D:\\data\\cache\\decompress\\url\\url.txt"), StandardCharsets.UTF_8);
        String encodingContent = EncryptUtils.encryptWithAESKey(sourceContent, secret);
        System.out.println(encodingContent);
        sourceContent = EncryptUtils.decryptWithAESKey(encodingContent, secret);
        System.out.println(sourceContent);
    }

    @Test
    public void testRandom() {
        String str = RandomUtils.getRandomStr(16);
        System.out.println(str.getBytes().length);
        int num = RandomUtils.getUnsignedRandomInteger(100);
        System.out.println(num);
    }

    @Test
    public void testAESCBC() {
        var secret = EncryptUtils.generateAESKey();
        var iv = Base64.encodeBase64String(RandomUtils.getRandomIV().getBytes());
        String encoding = EncryptUtils.encryptWithAESKey("abc", secret, iv);
        System.out.println(encoding);
        System.out.println(encoding.equals("odj5aBU8m1lIYE7f980RYQ=="));
        String source = EncryptUtils.decryptWithAESKey(encoding, secret, iv);
        System.out.println(source);
    }

    @Test
    public void testGenerateRSAKey() throws IOException {
        var publicKey = new File("D:\\data\\cache\\decompress\\url\\public.txt");
        var privateKey = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        Pair<String, String> pair = EncryptUtils.generateKeyPair();
        String publiKeyString = pair.getKey();
        String privateKeyString = pair.getValue();
        FileUtils.writeStringToFile(publicKey, publiKeyString, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(privateKey, privateKeyString, StandardCharsets.UTF_8);
    }


    @Test
    public void testDecryptJsEncodingContent() throws IOException {
        var encodingContent = "RavXxcj5F/wYnXzmRCojb8JI3DG0TPS5vorH7ABaLum0fUAlSQt2OV/YEs8MrmnB5u6sYEWWrOu9+9Ci/0eHHbeoO87C5T0pj4/qC6cCu0dSEh+gqbltEvWOlB4FF/7O40Vwupxvg8YEB2WfP0j52/Iv/pAZd1yu0lrK12IWRME=";
        var privateKey = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        String keyString = FileUtils.readFileToString(privateKey, StandardCharsets.UTF_8);
        String source = EncryptUtils.decryptWithPrivateKey(keyString, encodingContent);
        System.out.println(source);
    }

    @Test
    public void testEncryptWithPrivateKey() throws IOException {
        var privateKey = new File("D:\\data\\cache\\decompress\\url\\private.txt");
        String privateKeyString = FileUtils.readFileToString(privateKey, StandardCharsets.UTF_8);
        String encodingContent = EncryptUtils.encryptWithPrivateKey(privateKeyString, "abc");
        System.out.println(encodingContent);
        var publicKey = new File("D:\\data\\cache\\decompress\\url\\public.txt");
        String publicKeyString = FileUtils.readFileToString(publicKey, StandardCharsets.UTF_8);
        String source = EncryptUtils.decryptWithPublicKey(publicKeyString, encodingContent);
        System.out.println(source);

    }


}
