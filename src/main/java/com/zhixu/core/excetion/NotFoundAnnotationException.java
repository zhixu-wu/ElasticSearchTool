package com.zhixu.core.excetion;

import lombok.Data;

/**
 * @author wuzhixu
 * @date 2023/10/9
 * 描述:
 */
@Data
public class NotFoundAnnotationException extends RuntimeException {
    private String message;
    private Throwable cause;


    public NotFoundAnnotationException(String message) {
        super(message);
        this.message = message;
    }

    public NotFoundAnnotationException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public NotFoundAnnotationException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }
}
