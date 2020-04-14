package com.example.controller.test.controllerdemo.exception;

import com.example.controller.test.controllerdemo.constant.ResponseResult;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e){
        ResponseResult result = ResponseResult.fail(500,e.getMessage());
        return result;
    }
    /**
     * 拦截业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult handleBusinessException(BusinessException e){
        return ResponseResult.fail(500,e.getMessage());
    }
    /**
     * 拦截参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        StringBuilder errorMessage = new StringBuilder();
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)){
            for (int i=0;i < objectErrors.size();i++){
                if (i == 0){
                    errorMessage.append(objectErrors.get(i).getDefaultMessage());
                } else {
                    errorMessage.append(",").append(objectErrors.get(i).getDefaultMessage());
                }
            }
        } else {
            errorMessage.append(e.getMessage());
        }
        return ResponseResult.fail(400,errorMessage.toString());

    }



}
