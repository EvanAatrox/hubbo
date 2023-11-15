import CryptoJS from 'crypto-js'
import { randomAESKey, randomString, nilError } from '@/utils/StringUtils.ts'
import { readonly } from 'vue'


// AES 加密信息
const aes = readonly({
    key: randomAESKey(),
    iv: randomString(16),
    time: new Date().getTime(),
})

// CBC
const DEFAULT_AES_MODE = CryptoJS.mode.CBC

// 填充
const DEFAULT_DES_PADDING = CryptoJS.pad.Pkcs7


export function encryptWithAESCBC(rawContent: any, secret: string, iv: string): string {
    nilError(rawContent)
    // @ts-ignore
    return CryptoJS.AES.encrypt(rawContent, CryptoJS.enc.Utf8.parse(secret), {
        iv: CryptoJS.enc.Utf8.parse(iv),
        DEFAULT_AES_MODE,
        DEFAULT_DES_PADDING,
    }).toString()
}

export function decryptWithAESCBC(encodingContent: string, secret: string, iv: string) {
    nilError(encodingContent)
    //@ts-ignore
    return CryptoJS.AES.decrypt(encodingContent, CryptoJS.enc.Utf8.parse(secret), {
        iv: CryptoJS.enc.Utf8.parse(iv),
        DEFAULT_AES_MODE,
        DEFAULT_DES_PADDING,
    }).toString(CryptoJS.enc.Utf8)
}


/**
 * 计算md5值
 * @param content 文本内容
 */
export function md5(content: string): string {
    return CryptoJS.MD5(content).toString()
}

export default () => {
    const obj = {
        name: 'test',
    }
    let rawContent = new Date().getTime() + ''
    console.log(rawContent)
    const encodingResult = encryptWithAESCBC(rawContent, aes.key, aes.iv)
    console.log(encodingResult)
    const res = decryptWithAESCBC(encodingResult, aes.key, aes.iv)
    console.log(res)

}