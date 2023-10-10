package com.zhixu.core.enums;

import lombok.Getter;

/**
 * @author wuzhixu
 * @date 2023/10/10
 * 描述:
 */
@Getter
public enum ApiStatus {
    SYSTEM_SUCCESS(0, "success"),
    SYSTEM_ERROR(999, "error"),
    HTTP_200(200, "success"),
    HTTP_500(500, "error");

    private Integer code;
    private String msg;

    ApiStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
