package com.zhixu.core.mapper;

import com.zhixu.core.operate.DSL;
import com.zhixu.core.operate.query.QueryDSL;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
public interface BaseMapper<T> {

    int insert(T entity);

    int insert(DSL objJsonStr);

    int deleteById(String id);

    int deleteById(T entity);

    int delete(DSL dsl);

    int deleteBatchIds(Collection<String> idList);

    int updateById(T entity);

    int update(T entity, QueryDSL dsl);

    T selectById(Serializable id);

//    List<T> selectBatchIds(@Param("coll") Collection<? extends Serializable> idList);
//
//    List<T> selectByMap(@Param("cm") Map<String, Object> columnMap);
//
//    default T selectOne(@Param("ew") Wrapper<T> queryWrapper) {
//        List<T> list = this.selectList(queryWrapper);
//        if (list.size() == 1) {
//            return list.get(0);
//        } else if (list.size() > 1) {
//            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
//        } else {
//            return null;
//        }
//    }
//
//    default boolean exists(Wrapper<T> queryWrapper) {
//        Long count = this.selectCount(queryWrapper);
//        return null != count && count > 0L;
//    }
//
//    Long selectCount(@Param("ew") Wrapper<T> queryWrapper);
//
    List<T> selectList(@NonNull DSL dsl);
//
//    List<Map<String, Object>> selectMaps(@Param("ew") Wrapper<T> queryWrapper);
//
//    List<Object> selectObjs(@Param("ew") Wrapper<T> queryWrapper);
//
//    <P extends IPage<T>> P selectPage(P page, @Param("ew") Wrapper<T> queryWrapper);
//
//    <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, @Param("ew") Wrapper<T> queryWrapper);


}
