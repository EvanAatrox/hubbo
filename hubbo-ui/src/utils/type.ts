import { types } from 'sass'
import String = types.String

interface KeyInfo {
    // 密钥,AES密钥或者RSA的公钥
    key: string,
    // 获取到密钥的时间,长时间未更新需要强制更新密钥
    time: number,
    // 密钥来源
    from: string,
    // 偏移量
    iv?: string
}


class AESObject implements KeyInfo {

    constructor(public key: String, public iv: string, public time: number, public from: string) {
        this.key = key
        this.iv = iv
        this.from = from
        this.time = time
    }


    public toString(): string {
        return 'AESObject'
    }
}

class RSAObject implements KeyInfo {

    private iv?: string = undefined
    constructor(public key: String, public time: number, public from: string) {
        this.key = key
        this.from = from
        this.time = time
    }

    public toString(): string {
        return 'RSAObject'
    }

}

// export type只能用于导出类型声明，例如接口、类型别名、枚举等,不能导出类,对象
export type { KeyInfo }

// 导出类
export { AESObject, RSAObject }