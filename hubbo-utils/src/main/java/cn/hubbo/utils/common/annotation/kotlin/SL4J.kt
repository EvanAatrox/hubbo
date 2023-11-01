package cn.hubbo.utils.common.annotation.kotlin

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @Package cn.hubbo.utils.common.annotation.kotlin
 * @author 张晓华
 * @date 2023/11/1 22:29
 * @version V1.0
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class SL4J() {
    companion object {
        val <reified T> T.log: Logger inline get() = LoggerFactory.getLogger(T::class.java);
    }
}
