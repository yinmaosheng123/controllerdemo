package com.example.controller.test.controllerdemo.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum StatusCode {
    /**
     * 操作成功
     */
    SUCCESS(200,"success"),
    /**
     * 新增成功
     */
    ADD_SUCCESS(204,"success"),
    /**
     * 操作失败
     */
    FAIL(-1,"fail"),
    /**
     * 资源不存在
     */
    NOT_FOUND(404,"resource not found"),
    /**
     * 没有权限访问
     */
    NOT_AUTH(401,"没有权限访问"),
    /**
     * 未知错误
     */
    ERROR(500,"未知错误")
    ;

    private int code;
    private String message;
    private StatusCode(int code,String message){
        this.code = code;
        this.message = message;
    }


}
