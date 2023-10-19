package cn.hubbo.utils.date;

import java.util.Objects;

/**
 * @author 张晓华
 * @date 2023-10-19 10:51
 * @usage 当前类的用途描述
 */
public final class TimeUtils {

    /**
     * 统计单次任务的执行时间
     * @param runnable 可执行的任务
     * @return 执行时间
     */
    public static Long execute(Runnable runnable) {
        Objects.requireNonNull(runnable, "传入的任务不能为空");
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        return end - start;
    }
    
    public static Long execute(Runnable runnable, int repeatTimes) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < repeatTimes; i++) {
            runnable.run();
        }
        long end = System.currentTimeMillis();
        return end - start;
    }
    
    
    
    
    
}
