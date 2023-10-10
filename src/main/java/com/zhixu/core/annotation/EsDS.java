package com.zhixu.core.annotation;

import java.lang.annotation.*;

/**
 * @author wuzhixu
 * @date 2023/5/25
 * 描述:
 */

@Inherited
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsDS {
    String value() default "";

    /**
     * 数据源名称
     * @return
     */
    String dsName();

    /**
     * es 索引名称
     * @return
     */
    String index();
}
