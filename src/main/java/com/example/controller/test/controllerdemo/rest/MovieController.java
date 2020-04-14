package com.example.controller.test.controllerdemo.rest;

import com.example.controller.test.controllerdemo.constant.ResponseResult;
import com.example.controller.test.controllerdemo.entity.Movie;
import com.example.controller.test.controllerdemo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/movies/")
public class MovieController {
    @Resource
    private MovieService movieService;


    @PostMapping("addMovie")
    public ResponseResult addMovie(@RequestBody @Valid Movie movie){
        movieService.addMovie(movie);
        System.out.println("test");
        System.out.println(movie);
        return ResponseResult.success();
    }



}
