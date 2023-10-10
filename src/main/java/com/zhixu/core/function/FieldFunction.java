package com.zhixu.core.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
@FunctionalInterface
public interface FieldFunction<T,R> extends Function<T,R> , Serializable {
}
