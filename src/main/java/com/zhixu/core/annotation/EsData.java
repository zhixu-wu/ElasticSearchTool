package com.zhixu.core.annotation;

import com.zhixu.core.enums.FieldStyle;

import java.lang.annotation.*;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 * <p>
 * 标注实体类
 */

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsData {

    String value() default "";

    FieldStyle fieldStyle();

    String indexName();
}
