package com.zhixu.core.operate.query;

import com.zhixu.core.enums.Order;
import com.zhixu.core.enums.Queries;
import com.zhixu.core.function.FieldFunction;
import com.zhixu.core.operate.DSL;
import com.zhixu.core.operate.query.vo.RangeVO;
import lombok.NonNull;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
public interface Query<T, Children> extends DSL {

    Children eq(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children eq(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children ne(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children ne(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children or(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    <R> Children or(@NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    Children orderByAsc(@NonNull String field);

    <R> Children orderByAsc(@NonNull FieldFunction<T, R> field);

    Children orderByDesc(@NonNull String field);

    <R> Children orderByDesc(@NonNull FieldFunction<T, R> field);

    Children must(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children must(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children mustNot(@NonNull String key, @NonNull String value, @NonNull Queries queries);

    <R> Children mustNot(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries);

    Children mustShould(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    <R> Children mustShould(@NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries);

    Children range(@NonNull String field, @NonNull RangeVO rangeVO);

    <R> Children range(@NonNull FieldFunction<T, R> field, @NonNull RangeVO rangeVO);

    Children exists(@NonNull String fieldValue);

    <R> Children exists(@NonNull FieldFunction<T, R> fieldValue);

    Children notExists(@NonNull String fieldValue);

    Children size(int size);

    Children from(int from);

    Children sort(@NonNull String field, @NonNull Order order);

    <R> Children sort(@NonNull FieldFunction<T, R> field, @NonNull Order order);

    Children source(@NonNull String... sourceKey);

    <R> Children source(@NonNull FieldFunction<T, R>... sourceKey);


}
