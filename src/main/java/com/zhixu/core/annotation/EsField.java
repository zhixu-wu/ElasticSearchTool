package com.zhixu.core.annotation;

import com.zhixu.core.enums.FieldStyle;

import java.lang.annotation.*;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */

@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsField {

    String value() default "";

    boolean skip() default false;

    FieldStyle fieldStyle() default FieldStyle.DEFAULT;
}
