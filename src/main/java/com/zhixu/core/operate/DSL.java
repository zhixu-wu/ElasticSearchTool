package com.zhixu.core.operate;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
public interface DSL<T> {

    String DSL();

    Class<T> getEntityClass();
}
