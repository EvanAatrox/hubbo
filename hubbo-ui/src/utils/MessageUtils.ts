import {ElMessage} from 'element-plus'

export function success(msg: any): void {
    ElMessage.success(msg)
}


export function error(message: any): void {
    ElMessage({
        duration: 5000,
        message: message,
        type: 'error',
        center: true,
    })
}