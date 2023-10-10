package com.zhixu.core.enums;

import lombok.Getter;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
@Getter
public enum Range {
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL_TO("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL_TO("lte");

    private String value;

    Range(String value) {
        this.value = value;
    }
}
