package com.example.controller.test.controllerdemo.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Actor {
    @NotEmpty(message = "演员名称不能为空")
    private String name;
    @NotNull(message = "演员年龄不能为空")
    private String age;
}
