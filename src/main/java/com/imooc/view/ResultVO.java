package com.imooc.view;

import lombok.Data;

/**
 * HTTP请求返回的最外层对象
 *
 * @author Zhang Pengnian
 * @since 2019-10-14 16:00
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private T data;

}
