package com.zhixu.core.excetion;

/**
 * @author wuzhixu
 * @date 2023/10/10
 * 描述:
 */
public class OperateException extends RuntimeException {

    private String message;
    private Throwable cause;


    public OperateException(String message) {
        super(message);
        this.message = message;
    }

    public OperateException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public OperateException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

}
