package com.zhixu.core.enums;

import lombok.Getter;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
@Getter
public enum Order {
    ASC("asc"),
    DESC("desc");

    private String value;

    Order(String value) {
        this.value = value;
    }
}
