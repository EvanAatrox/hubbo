import { nanoid } from 'nanoid'


// @ts-ignore
const base_line: string = 'adefbcgABCDWXYTopqrUOPQRSVZ0E12345FGHIJKLMN6789hijklmnstuvwxyz'


/**
 * 生成一个大于0小于指定值的随机整数
 */
export function randomUnsignedInteger(bound: number = 10): number {
    return parseInt(String(Math.random() * bound))
}

/**
 * 使用nanoID生成随机字符串
 */
export function uuid(trimSymbol?: boolean, len?: number): string {
    let res: string
    if (len) {
        res = nanoid(len)
    } else {
        res = nanoid()
    }
    return trimSymbol ? res.replaceAll('-', '').replaceAll('_', '') : res
}

export function randomString(len: number) {
    let str = ''
    for (let i = 0; i < len; i++) {
        const randomNum = randomUnsignedInteger(base_line.length)
        str = str.concat(base_line.charAt(randomNum))
    }
    return str
}

export function randomAESKey(): string {
    return randomString(6).concat(uuid(true, 4)).concat(randomString(6))
}


export function toString(source: any): string | undefined {
    if (!source) {
        return undefined
    }
    if (source  ! instanceof String) {
        return JSON.stringify(source)
    }
    return source
}

export function nilError(str: string, msg?: string) {
    if (!str) {
        throw new Error(msg ? msg : `method parameter can't be null`)
    }
    return str
}