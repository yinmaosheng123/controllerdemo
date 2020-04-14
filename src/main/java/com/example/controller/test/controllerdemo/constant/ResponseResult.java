package com.example.controller.test.controllerdemo.constant;

import com.example.controller.test.controllerdemo.enums.StatusCode;
import lombok.Data;

import java.util.SplittableRandom;

@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    int code;
    /**
     * 描述信息
     */
    String message;
    /**
     * 接口返回数据
     */
    T data;

    private ResponseResult(){
        this(200,"success");
    }

    private ResponseResult(int code,String message){
        this.code = code;
        this.message = message;
    }
    private ResponseResult(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
    }
    private ResponseResult(int code,String message,T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
    private ResponseResult(StatusCode statusCode,T data){
        this.code = statusCode.getCode();
        this.message = statusCode.getMessage();
        this.data = data;
    }
    public static ResponseResult success(){
        return new ResponseResult();
    }
    public static <T> ResponseResult success(T data) {
        return success(StatusCode.SUCCESS.getCode(),"success",data);
    }
    public static ResponseResult success(int code,String message) {
        return success(code,message,null);
    }
    public static ResponseResult success(StatusCode statusCode) {
        return success(statusCode.getCode(),statusCode.getMessage(),null);
    }

    public static <T> ResponseResult success(StatusCode statusCode,T data) {
        return success(statusCode.getCode(),statusCode.getMessage(),data);
    }

    public static <T> ResponseResult success(int code,String message,T data) {
        return new ResponseResult(code,message,data);
    }

    public static ResponseResult fail() {
        return fail(StatusCode.FAIL.getCode(),StatusCode.FAIL.getMessage());
    }

    public static ResponseResult fail(int code,String message) {
        return fail(code,message,null);
    }

    public static ResponseResult fail(StatusCode statusCode) {
        return fail(statusCode.getCode(),statusCode.getMessage(),null);
    }

    public static <T> ResponseResult fail(StatusCode statusCode, T data) {
        return fail(statusCode.getCode(),statusCode.getMessage(),data);
    }

    public static <T> ResponseResult fail(int code,String message,T data) {
        return new ResponseResult(code,message,data);
    }




}
