import CryptoJS from 'crypto-js'

const aesKeyInfo = {
    key: '0123456789abcdef',
    iv: 'abcdef0123456789',
}
const defaultAesMode = CryptoJS.mode.CBC
const defaultAesPadding = CryptoJS.pad.Pkcs7
const defaultDesMode = CryptoJS.mode.CBC
const defaultDesPadding = CryptoJS.pad.Pkcs7


function encrypt(data, key, iv) {
    const encodingContent = CryptoJS.AES.encrypt(data, CryptoJS.enc.Utf8.parse(key), {
        iv: CryptoJS.enc.Utf8.parse(iv),
        defaultAesMode,
        defaultDesPadding,
    })
    return encodingContent
}


function encryptWithAESCBC(rawContent: string, secret: string, iv: string): string {
    // @ts-ignore
    return CryptoJS.AES.encrypt(rawContent, CryptoJS.enc.Utf8.parse(secret), {
        iv: CryptoJS.enc.Utf8.parse(iv),
        defaultAesMode,
        defaultDesPadding,
    }).toString()
}

function decryptWithAESCBC(encodingContent: string, secret: string, iv: string) {
    //@ts-ignore
    return CryptoJS.AES.decrypt(encodingContent, CryptoJS.enc.Utf8.parse(secret), {
        iv: CryptoJS.enc.Utf8.parse(iv),
        defaultAesMode,
        defaultDesPadding,
    }).toString(CryptoJS.enc.Utf8)
}

export default () => {
    const encodingResult = encryptWithAESCBC('abc', aesKeyInfo.key, aesKeyInfo.iv).toString()
    console.log(encodingResult)
    const rawContent = decryptWithAESCBC(encodingResult, aesKeyInfo.key, aesKeyInfo.iv);
    console.log(rawContent)
}