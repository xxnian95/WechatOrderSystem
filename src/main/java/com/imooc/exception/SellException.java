package com.imooc.exception;

import com.imooc.enums.ResultEnum;

/**
 * @author Zhang Pengnian
 * @since 2019-10-15 15:00
 */
public class SellException extends RuntimeException{

    private Integer code;

    /**
     * @param resultEnum 结果的枚举
     */
    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
}
