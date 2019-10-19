package com.imooc.exception;

import com.imooc.enums.ResultEnum;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 15:00
 */
public class SellException extends RuntimeException{

    private Integer code;

    private String message;

    /**
     * @param resultEnum 结果的枚举
     */
    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public SellException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
