package com.zhixu.core.operate.query;

import com.zhixu.core.enums.FieldStyle;
import com.zhixu.core.enums.Order;
import com.zhixu.core.enums.Queries;
import com.zhixu.core.function.FieldFunction;
import lombok.NonNull;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
public interface SimpleBoolQuery<T,Children> extends BoolQuery<T,Children> {

    Children eq(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children eq(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children ne(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children ne(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children or(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    <R> Children or(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    Children orderBy(@NonNull String field, @NonNull Order order);

    <R> Children orderBy(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull Order order);

}
