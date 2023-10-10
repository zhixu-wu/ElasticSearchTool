package com.zhixu.core.mapper;

import com.zhixu.core.operate.DSL;
import com.zhixu.core.operate.query.QueryDSL;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
public abstract class EsSimpleImpl<T> implements BaseMapper<T>{

    @Override
    public int insert(T entity) {
        return 0;
    }

    @Override
    public int insert(DSL objJsonStr) {
        System.out.println(objJsonStr.DSL());

        return 0;
    }

    @Override
    public int deleteById(String id) {
        return 0;
    }

    @Override
    public int deleteById(T entity) {
        return 0;
    }

    @Override
    public int delete(DSL dsl) {
        return 0;
    }

    @Override
    public int deleteBatchIds(Collection<String> idList) {
        return 0;
    }

    @Override
    public int updateById(T entity) {
        return 0;
    }

    @Override
    public int update(T entity, QueryDSL dsl) {
        return 0;
    }

    @Override
    public T selectById(Serializable id) {
        return null;
    }

    @Override
    public List<T> selectList(@NonNull DSL dsl) {
        return null;
    }
}