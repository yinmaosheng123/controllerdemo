package com.example.controller.test.controllerdemo.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Movie {
    private String id;
    @NotEmpty(message = "Movie name cannot be empty")
    private String name;
    @NotNull(message = "电影时长不能为空")
    private Integer duration;
    @NotNull(message = "演员不能为空")
    @NotEmpty(message = "演员不能为空")
    private List<@Valid Actor> actors;
    @NotEmpty(message = "电影描述不能为空")
    private String description;



}
