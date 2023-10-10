package com.zhixu.core.excetion;

import lombok.Data;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
@Data
public class JsonToEntityException extends RuntimeException {
    private String message;
    private Throwable cause;


    public JsonToEntityException(String message) {
        super(message);
        this.message = message;
    }

    public JsonToEntityException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public JsonToEntityException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }
}
