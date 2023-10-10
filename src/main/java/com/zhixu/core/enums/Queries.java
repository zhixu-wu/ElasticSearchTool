package com.zhixu.core.enums;

import lombok.Getter;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
@Getter
public enum Queries {
    TERM("term"),
    TERMS("terms"),
    RANGE("range"),
    MATCH("match"),
    MATCH_PHRASE("match_phrase"),
    MATCH_ALL("match_all"),
    PREFIX("prefix"),
    WILDCARD("wildcard"),
    REGEXP("regexp"),
    FUZZY("fuzzy"),
    MULTI_MATCH("multi_match");

    private String value;
    Queries(String value) {
        this.value = value;
    }

}
