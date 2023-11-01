package cn.hubbo.utils.system;

import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;

/**
 * @author 张晓华
 * @date 2023-11-01 10:30
 * @usage 系统工具类，如系统负载，进程相关信息
 */
public final class SystemUtils {
    
    
    public static void loadInfo() {
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        System.out.println(cpuInfo);
        String cpuModel = cpuInfo.getCpuModel();
        System.out.println(cpuModel);
    }
    
    
    
}
