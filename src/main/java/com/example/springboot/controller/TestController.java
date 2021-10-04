package com.example.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "test", consumes = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
    //private final EmployeeRepository repository;

    @Autowired
    TestController() {
        //this.repository = repository;
    }

    @GetMapping()
    List<String> all() {
        return List.of("A", "B");
    }

}


