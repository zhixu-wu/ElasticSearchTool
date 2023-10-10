package com.zhixu.core.excetion;

import lombok.Data;

/**
 * @author wuzhixu
 * @date 2023/10/8
 * 描述:
 */
@Data
public class LoadDataSourceException extends RuntimeException {
    private String message;
    private Throwable cause;


    public LoadDataSourceException(String message) {
        super(message);
        this.message = message;
    }

    public LoadDataSourceException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public LoadDataSourceException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

}
