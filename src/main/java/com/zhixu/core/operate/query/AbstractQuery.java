package com.zhixu.core.operate.query;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zhixu.core.annotation.EsData;
import com.zhixu.core.annotation.EsField;
import com.zhixu.core.enums.FieldStyle;
import com.zhixu.core.enums.Queries;
import com.zhixu.core.excetion.NotFoundAnnotationException;
import com.zhixu.core.excetion.NotFoundFieldException;
import com.zhixu.core.function.FieldFunction;
import com.zhixu.core.util.ClassUtils;
import lombok.NonNull;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
public abstract class AbstractQuery<T, Children> {
    private Class<T> entityClass;

    protected JSONObject bool;

    protected JSONArray sort;

    protected JSONObject aggs;

    protected JSONArray source;

    protected int size = 10;

    protected int from = 0;





    public AbstractQuery(Class<T> entityClass) {

        if (entityClass == null) {
            throw new NullPointerException("entityClass is null");
        }

        if (ClassUtils.getAnnotate(entityClass, EsData.class) == null) {
            throw new NotFoundAnnotationException(String.format("not found EsData annotation, ObjName: %s", entityClass.getName()));
        }

        this.entityClass = entityClass;


        this.bool = new JSONObject();
        bool.put("must", new JSONArray());
        bool.put("filter", new JSONArray());
        bool.put("should", new JSONArray());
        bool.put("must_not", new JSONArray());
    }


    public Class<T> getEntityClass() {
        return entityClass;
    }

    private FieldStyle getClassFileStyle() {
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


    public <R> String getField(@NonNull FieldFunction<T, R> field) {
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

            if (!ClassUtils.allFields(getEntityClass()).contains(methodName)) {
                throw new NotFoundFieldException(String.format("not found field: %s", methodName));
            }

            FieldStyle fieldStyle = getFieldStyle(methodName);

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


    private FieldStyle getFieldStyle(@NonNull String fieldName) {
        EsField esField = ClassUtils.getFieldAnnotation(entityClass, fieldName, EsField.class);

        if (esField != null) {
            return esField.fieldStyle();
        }

        EsData esData = ClassUtils.getAnnotate(entityClass, EsData.class);
        return esData.fieldStyle();
    }


    protected JSONObject addQueries(Queries queries, String key, String value) {
        JSONObject body = new JSONObject();
        JSONObject match_phrase = new JSONObject();
        match_phrase.put(key, value);
        body.put(queries.getValue(), match_phrase);
        return body;
    }

    protected JSONArray getMust() {
        return this.bool.getJSONArray("must");
    }

    protected void addMust(JSONObject json) {
        getMust().add(json);
    }

    protected void addMust(Queries queries, String key, String value) {
        getMust().add(addQueries(queries, key, value));
    }


    protected JSONArray getMustNot() {
        return this.bool.getJSONArray("must_not");
    }

    protected void addMustNot(JSONObject json) {
        getMustNot().add(json);
    }

    protected void addMustNot(Queries queries, String key, String value) {
        getMustNot().add(addQueries(queries, key, value));
    }

    protected JSONObject getBoolShould() {
        JSONObject body = new JSONObject();
        JSONObject bool = new JSONObject();
        bool.put("should", new JSONArray());
        body.put("bool", bool);
        return body;
    }


}