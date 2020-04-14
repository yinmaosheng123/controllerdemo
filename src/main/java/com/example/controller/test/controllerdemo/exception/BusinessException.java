package com.example.controller.test.controllerdemo.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private String message;
    private Throwable throwable;
    public BusinessException(String message){
        this(message,null);
    }
    public BusinessException(String message,Throwable throwable){
        super(message,throwable);
    }


}
