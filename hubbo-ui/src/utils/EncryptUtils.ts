import CryptoJS from 'crypto-js'
import { randomAESKey, randomString, nilError } from '@/utils/StringUtils.ts'
import { readonly } from 'vue'
import { JSEncrypt } from 'jsencrypt'
// 引入接口
import type { KeyInfo } from '@/utils/type.ts'
// 引入类
import { AESObject, RSAObject } from '@/utils/type.ts'


// CBC
const DEFAULT_AES_MODE = CryptoJS.mode.CBC

// 填充
const DEFAULT_DES_PADDING = CryptoJS.pad.Pkcs7

// AES 加密信息
const aes = readonly({
    key: randomAESKey(),
    iv: randomString(16),
    /* 生成时间 */
    time: new Date().getTime(),
    /* 来源 */
    from: 'https://localhost:80',
})

// RSA加密信息
const rsa = readonly({
    publiKey: `MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKRg6k8EGlMS/HgsXGezISxOoGz1TXdrkSpQwRKnHfbzFgwKiENobNfN1QGc3Egb9dWQio1XfMN8cZgWCxZAjrl27ASGZtya8yKtJIulyUeDMhH28CLQ0xPcqVm4VXdAi9LZIK32FKkuB7NKkpwuiQkVKZsRF5FRAl+oMA2q0A5QIDAQAB`,
    /* 获取时间 */
    time: new Date().getTime(),
    /* 来源 */
    from: 'https://hubbo.cn',
})


/**
 * 获取AES加密信息
 */
function getAESInfo(): KeyInfo {
    // TODO 后面可以改动为从Pinia或者随机生成AES信息,暂时空着后面扩展
    return new AESObject(aes.key, aes.iv, aes.time, aes.from)
}

/**
 *  获取RSA信息
 */
function getRsaInfo(): KeyInfo {
    return new RSAObject(rsa.publiKey, rsa.time, rsa.from)
}

/**
 * 获取RSA加密工具
 */
function getEncryptor(): JSEncrypt {
    const publicKeyString = rsa.publiKey
    const rsaInfo: KeyInfo = getRsaInfo()
    const jsEncrypt = new JSEncrypt()
    jsEncrypt.setPublicKey(rsaInfo.key)
    return jsEncrypt
}


/**
 * AES加密
 * @param rawContent 原始文本
 * @return 加密后的文本
 */
export function encryptWithAESCBC(rawContent: any): string {
    nilError(rawContent)
    const aesObject: KeyInfo = getAESInfo()
    // @ts-ignore
    return CryptoJS.AES.encrypt(rawContent, CryptoJS.enc.Utf8.parse(aesObject.key), {
        iv: CryptoJS.enc.Utf8.parse(aesObject.iv),
        DEFAULT_AES_MODE,
        DEFAULT_DES_PADDING,
    }).toString()
}

/**
 * AES解密
 * @param encodingContent 加密后的文本
 * @return 原始文本
 */
export function decryptWithAESCBC(encodingContent: string): string {
    nilError(encodingContent)
    const aesObject: KeyInfo = getAESInfo()
    //@ts-ignore
    return CryptoJS.AES.decrypt(encodingContent, CryptoJS.enc.Utf8.parse(aesObject.key), {
        iv: CryptoJS.enc.Utf8.parse(aesObject.iv),
        DEFAULT_AES_MODE,
        DEFAULT_DES_PADDING,
    }).toString(CryptoJS.enc.Utf8)
}


/**
 * 计算md5值
 * @param content 文本内容
 * @return md5值
 */
export function md5(content: string): string {
    return CryptoJS.MD5(content).toString()
}

/**
 *RSA公钥加密
 * @param rawContent 原始文本信息
 * @return 成功则有值,否则返回false
 */
export function encryptWithRSAPublicKey(rawContent: string): string | boolean {
    nilError(rawContent)
    return getEncryptor().encrypt(rawContent)
}


/**
 * RSA公钥解密
 * @param encodingContent RSA私钥加密的文本
 * @return 原始文本
 */
export function decryptWithRSAPublicKey(encodingContent: string) {
    nilError(encodingContent)
    return getEncryptor().decrypt(encodingContent)
}


export default () => {
    console.log('===========AES==========')
    const encodingContent = encryptWithAESCBC('abc')
    const source = decryptWithAESCBC(encodingContent)
    console.log(encodingContent)
    console.log(source)
    console.log('default fun')
}

