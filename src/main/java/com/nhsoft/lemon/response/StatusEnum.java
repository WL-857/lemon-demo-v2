package com.nhsoft.lemon.response;

/**
 * @author wanglei
 */

public enum StatusEnum {

    SUCCESS(200,"操作成功"),
    FAIL(300,"操作失败"),
    NO_OBJECT(400,"没有该对象"),
    NO_PARAMETER(401,"参数为空");
    private int code;

    private String message;

    StatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "StatusEnum{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
