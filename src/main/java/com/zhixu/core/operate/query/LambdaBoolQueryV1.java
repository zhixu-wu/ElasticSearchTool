package com.zhixu.core.operate.query;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.zhixu.core.enums.FieldStyle;
import com.zhixu.core.enums.Order;
import com.zhixu.core.enums.Queries;
import com.zhixu.core.enums.Range;
import com.zhixu.core.function.FieldFunction;
import com.zhixu.core.operate.query.vo.RangeVO;
import lombok.NonNull;


/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
public class LambdaBoolQueryV1<T> extends AbstractBool<T> implements SimpleBoolQuery<T, LambdaBoolQueryV1<T>> {
    private JSONObject bool;

    private JSONArray sort;

    private JSONObject aggs;

    private JSONArray source;

    private int size = 10;

    private int from = 0;


    public LambdaBoolQueryV1(Class<T> clazz) {
        super(clazz);
        this.bool = new JSONObject();
        bool.put("must", new JSONArray());
        bool.put("filter", new JSONArray());
        bool.put("should", new JSONArray());
        bool.put("must_not", new JSONArray());
    }

    @Override
    public String DSL() {
        JSONObject body = new JSONObject();
        body.put("size", this.size);
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
    public LambdaBoolQueryV1<T> eq(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        must(key, value, queries);

        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> eq(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        must(fieldStyle, field, value, queries);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> ne(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        mustNot(key, value, queries);

        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> ne(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        mustNot(fieldStyle, field, value, queries);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> or(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
        mustShould(matchField, array, minimum_should_match, queries);

        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> or(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
        mustShould(fieldStyle, field, array, minimum_should_match, queries);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> orderBy(@NonNull String field, @NonNull Order order) {
        sort(field, order);

        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> orderBy(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull Order order) {
        sort(fieldStyle, field, order);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> must(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        addMust(queries, key, value);

        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> must(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        addMust(queries, getField(field, fieldStyle), value);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> mustNot(@NonNull String key, @NonNull String value, @NonNull Queries queries) {
        addMustNot(queries, key, value);
        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> mustNot(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String value, @NonNull Queries queries) {
        addMustNot(queries, getField(field, fieldStyle), value);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> mustShould(@NonNull String matchField, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
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
    public <R> LambdaBoolQueryV1<T> mustShould(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull String[] array, int minimum_should_match, @NonNull Queries queries) {
        mustShould(getField(field, fieldStyle), array, minimum_should_match, queries);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> range(@NonNull String field, @NonNull RangeVO rangeVO) {
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
    public <R> LambdaBoolQueryV1<T> range(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull RangeVO rangeVO) {
        range(getField(field, fieldStyle), rangeVO);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> exists(@NonNull String fieldValue) {
        JSONObject exists = new JSONObject();
        JSONObject field = new JSONObject();
        field.put("field", fieldValue);
        exists.put("exists", field);
        addMust(exists);

        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> exists(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> fieldValue) {
        exists(getField(fieldValue, fieldStyle));

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> notExists(@NonNull String fieldValue) {
        return null;
    }

    @Override
    public LambdaBoolQueryV1<T> size(int size) {
        this.size = size;
        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> from(int from) {
        this.from = from;
        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> sort(@NonNull String field, @NonNull Order order) {
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
    public <R> LambdaBoolQueryV1<T> sort(FieldStyle fieldStyle, @NonNull FieldFunction<T, R> field, @NonNull Order order) {
        sort(getField(field, fieldStyle), order);

        return this;
    }

    @Override
    public LambdaBoolQueryV1<T> source(@NonNull String... sourceKey) {
        if (source == null) {
            source = new JSONArray();
        }

        for (String key : sourceKey) {
            source.add(key);
        }

        return this;
    }

    @Override
    public <R> LambdaBoolQueryV1<T> source(FieldStyle fieldStyle, @NonNull FieldFunction<T, R>... sourceKey) {
        for (FieldFunction<T, R> key : sourceKey) {
            source(getField(key, fieldStyle));
        }

        return this;
    }

    private JSONObject addQueries(Queries queries, String key, String value) {
        JSONObject body = new JSONObject();
        JSONObject match_phrase = new JSONObject();
        match_phrase.put(key, value);
        body.put(queries.getValue(), match_phrase);
        return body;
    }

    private JSONArray getMust() {
        return this.bool.getJSONArray("must");
    }

    private void addMust(JSONObject json) {
        getMust().add(json);
    }

    private void addMust(Queries queries, String key, String value) {
        getMust().add(addQueries(queries, key, value));
    }


    private JSONArray getMustNot() {
        return this.bool.getJSONArray("must_not");
    }

    private void addMustNot(JSONObject json) {
        getMustNot().add(json);
    }

    private void addMustNot(Queries queries, String key, String value) {
        getMustNot().add(addQueries(queries, key, value));
    }

    private JSONObject getBoolShould() {
        JSONObject body = new JSONObject();
        JSONObject bool = new JSONObject();
        bool.put("should", new JSONArray());
        body.put("bool", bool);
        return body;
    }
}
