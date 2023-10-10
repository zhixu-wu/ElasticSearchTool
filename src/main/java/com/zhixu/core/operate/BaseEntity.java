package com.zhixu.core.operate;

import com.zhixu.core.annotation.EsData;
import com.zhixu.core.annotation.EsField;
import com.zhixu.core.enums.FieldStyle;
import com.zhixu.core.excetion.NotFoundAnnotationException;
import com.zhixu.core.util.ClassUtils;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wuzhixu
 * @date 2023/10/10
 * 描述:
 */
public interface BaseEntity<T> {

    /**
     * 获取字段对应在ES中的字段名称
     *
     * @return
     */
    default Map<String, String> getFields(@NonNull Class<T> clazz) {
        Set<String> fields = ClassUtils.allFields(clazz);

        if (fields.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> resultMap = new HashMap<>();

        for (String field : fields) {
            EsField esField = ClassUtils.getFieldAnnotation(clazz, field, EsField.class);

            if (esField != null) {
                resultMap.put(field, FieldStyle.getStyle(esField.fieldStyle(), field));
            } else {
                resultMap.put(field, field);
            }

        }

        return resultMap;
    }


    default String getIndexName(@NonNull Class<T> clazz) {
        EsData esData = ClassUtils.getAnnotate(clazz, EsData.class);

        if (esData == null) {
            throw new NotFoundAnnotationException(String.format("Class %s not found annotation EsData", clazz.getName()));
        }

        return esData.indexName();
    }
}
