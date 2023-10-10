package com.zhixu.core.operate.query;

import com.zhixu.core.annotation.EsData;
import com.zhixu.core.enums.FieldStyle;
import com.zhixu.core.excetion.NotFoundAnnotationException;
import com.zhixu.core.function.FieldFunction;
import com.zhixu.core.util.ClassUtils;
import lombok.NonNull;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
public abstract class AbstractBool<T> {

    private Class<T> entityClass;

    public AbstractBool(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public Class<T> getEntityClass() {
        return entityClass;
    }

    public FieldStyle getClassFileStyle() {
        try {
            EsData esData = ClassUtils.getAnnotate(getEntityClass(), EsData.class);

            if (esData != null) {
                return esData.fieldStyle();
            }

        } catch (Exception e) {
            throw new NotFoundAnnotationException("not found EsData.fieldStyle", e);
        }

        throw new NotFoundAnnotationException("not found EsData.fieldStyle");
    }


    public <R> String getField(@NonNull FieldFunction<T, R> field, FieldStyle fieldStyle) {
        try {


            Class clazz = field.getClass();
            Method method = clazz.getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda lambda = (SerializedLambda) method.invoke(field);
            String methodName = lambda.getImplMethodName();

            if (methodName.indexOf("get") < 0) {
                throw new RuntimeException("not found get method");
            }

            methodName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
            if (fieldStyle == null || FieldStyle.UNDER_LINE == fieldStyle) {
                return methodName.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
            } else if (FieldStyle.CAMEL_CASE == fieldStyle) {
                return methodName;
            }
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }

        throw new RuntimeException("not found field");
    }


}
