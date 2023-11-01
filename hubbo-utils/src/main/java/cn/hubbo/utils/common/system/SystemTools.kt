package cn.hubbo.utils.common.system

import oshi.SystemInfo

/**
 * @Package cn.hubbo.utils.system
 * @author 张晓华
 * @date 2023/11/1 20:24
 * @version V1.0
 * @Copyright © 2023-2025 版权所有，未经授权均为剽窃，作者保留一切权利
 */


class SystemTools {

    fun loadInfo() {
        val systemInfo = SystemInfo()
        val operatingSystem = systemInfo.operatingSystem
        println("operatingSystem $operatingSystem")
    }

}