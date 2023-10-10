package com.zhixu.core.operate.query;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zhixu.core.enums.Order;
import com.zhixu.core.enums.Queries;
import com.zhixu.core.enums.Range;
import com.zhixu.core.function.FieldFunction;
import com.zhixu.core.operate.query.vo.RangeVO;
import lombok.NonNull;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
public class LambdaBoolQuery<T> extends AbstractQuery<T, LambdaBoolQuery<T>> implements Query<T, LambdaBoolQuery<T>> {


    public LambdaBoolQuery(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public String DSL() {
        JSONObject body = new JSONObject();
        body.put("size", size);
        body.put("from", from);

        if (bool != null) {
            body.put("query", bool);
        }

        if (sort != null) {
            body.put("sort", sort);
        }

        if (aggs != null) {
            body.put("aggs", aggs);
        }

        if (source != null) {
            body.put("_source", source);
        }

        return body.toJSONString();
    }

    @Override
    public LambdaBoolQuery<T> eq(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        must(key, value, queries);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> eq(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        must(field, value, queries);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> ne(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        mustNot(key, value, queries);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> ne(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        mustNot(field, value, queries);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> or(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
        mustShould(matchField, array, minimum_should_match, queries);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> or(@NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
        mustShould( field, array, minimum_should_match, queries);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> orderByAsc(@NonNull String field) {
        sort(field, Order.ASC);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> orderByAsc(@NonNull FieldFunction<T, R> field) {
        sort(field, Order.ASC);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> orderByDesc(@NonNull String field) {
        sort(field, Order.DESC);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> orderByDesc(@NonNull FieldFunction<T, R> field) {
        sort(field, Order.DESC);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> must(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        addMust(queries, key, value);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> must(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        addMust(queries, getField(field), value);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> mustNot(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        addMustNot(queries, key, value);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> mustNot(@NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        addMustNot(queries, getField(field), value);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> mustShould(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
        JSONObject boolShould = getBoolShould();
        JSONArray should = boolShould.getJSONObject("bool").getJSONArray("should");

        //放入should
        for (int i = 0; i < array.length; i++) {
            should.add(addQueries(queries, matchField, array[i]));
        }

        boolShould.getJSONObject("bool").put("minimum_should_match", minimum_should_match);
        addMust(boolShould);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> mustShould(@NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
        mustShould(getField(field), array, minimum_should_match, queries);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> range(@NonNull String field, @NonNull RangeVO rangeVO) {
        if (rangeVO.getRange().isEmpty()) {
            return this;
        }

        JSONObject body = new JSONObject();
        JSONObject fieldBody = new JSONObject();
        JSONObject rangeBody = new JSONObject();

        for (Range range : rangeVO.getRange().keySet()) {
            rangeBody.put(range.getValue(), rangeVO.getRange().get(range));
        }

        fieldBody.put(field, rangeBody);
        body.put("range", fieldBody);
        addMust(body);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> range(@NonNull FieldFunction<T, R> field, @NonNull RangeVO rangeVO) {
        range(getField(field), rangeVO);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> exists(@NonNull String fieldValue) {
        JSONObject exists = new JSONObject();
        JSONObject field = new JSONObject();
        field.put("field", fieldValue);
        exists.put("exists", field);
        addMust(exists);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> exists(@NonNull FieldFunction<T, R> fieldValue) {
        exists(getField(fieldValue));

        return this;
    }

    @Override
    public LambdaBoolQuery<T> notExists(@NonNull String fieldValue) {
        //TODO
        return null;
    }

    @Override
    public LambdaBoolQuery<T> size(int size) {
        this.size = size;

        return this;
    }

    @Override
    public LambdaBoolQuery<T> from(int from) {
        this.from = from;

        return this;
    }

    @Override
    public LambdaBoolQuery<T> sort(@NonNull String field, @NonNull Order order) {
        if (sort == null) {
            this.sort = new JSONArray();
        }

        JSONObject sortBody = new JSONObject();
        JSONObject fieldBody = new JSONObject();
        fieldBody.put("order", order.getValue());
        sortBody.put(field, fieldBody);
        sort.add(sortBody);

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> sort(@NonNull FieldFunction<T, R> field, @NonNull Order order) {
        sort(getField(field), order);

        return this;
    }

    @Override
    public LambdaBoolQuery<T> source(@NonNull String... sourceKey) {
        if (source == null) {
            source = new JSONArray();
        }

        for (String key : sourceKey) {
            source.add(key);
        }

        return this;
    }

    @Override
    public <R> LambdaBoolQuery<T> source(@NonNull FieldFunction<T, R>... sourceKey) {
        for (FieldFunction<T, R> key : sourceKey) {
            source(getField(key));
        }

        return this;
    }
}
