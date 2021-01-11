package com.nhsoft.lemon.exception;

import com.nhsoft.lemon.response.ResponseMessage;
import com.nhsoft.lemon.response.StatusEnum;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author wanglei
 */
@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(GlobalException.class)
    public ResponseMessage handleGlobalException(GlobalException e,int code,String msg){
        ResponseMessage response = new ResponseMessage();
        response.setCode(code);
        response.setMessage(msg);
        return response;
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseMessage handleDuplicateKeyException(GlobalException e){
        return ResponseMessage.error("数据库中已存在该记录");
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseMessage handleParameterException(GlobalException e){
        return ResponseMessage.error("参数异常");
    }
}
