package com.nhsoft.lemon.exception;

import com.nhsoft.lemon.response.ResponseMessage;
import lombok.Data;

/**
 * @author wanglei
 */
@Data
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 错误码
     */
    private int code;

    public GlobalException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GlobalException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public GlobalException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public GlobalException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
