package com.example.controller.test.controllerdemo.service.Impl;

import com.example.controller.test.controllerdemo.entity.Movie;
import com.example.controller.test.controllerdemo.service.MovieService;
import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    @Override
    public void addMovie(Movie movie){
        System.out.println("test2");
        System.out.println(movie);
    }
}
