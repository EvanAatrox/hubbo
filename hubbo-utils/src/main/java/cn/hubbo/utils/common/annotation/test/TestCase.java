package cn.hubbo.utils.common.annotation.test;

import com.google.common.annotations.VisibleForTesting;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 张晓华
 * @date 2023-10-20 14:12
 * @usage 当前类的用途描述
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@Documented
public @interface TestCase {
    
    /* 测试用例名称 */
    String value() default "";
    
}


