package com.zhixu.core.operate.query;

import com.zhixu.core.enums.FieldStyle;
import com.zhixu.core.enums.Order;
import com.zhixu.core.enums.Queries;
import com.zhixu.core.function.FieldFunction;
import com.zhixu.core.operate.query.vo.RangeVO;
import lombok.NonNull;

/**
 * @author wuzhixu
 * @date 2023/10/7
 * 描述:
 */
public interface BoolQuery<T,Children> extends QueryDSL {

    Children must(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children must(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children mustNot(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children mustNot(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children mustShould(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    <R> Children mustShould(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    Children range(@NonNull String field, @NonNull RangeVO rangeVO);

    <R> Children range(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull RangeVO rangeVO);

    Children exists(@NonNull String fieldValue);

    <R> Children exists(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> fieldValue);

    Children notExists(@NonNull String fieldValue);

    Children size(int size);

    Children from(int from);

    Children sort(@NonNull String field, @NonNull Order order);

    <R> Children sort(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull Order order);

    Children source(@NonNull String... sourceKey);

    <R> Children source(FieldStyle fieldStyle, @NonNull FieldFunction<T, R>... sourceKey);
}