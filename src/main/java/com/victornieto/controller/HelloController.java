package com.victornieto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by victor.nieto.castan on 29/03/2017.
 */
@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @RequestMapping("/")
    public String helloWorld(){
        return "Hello World!!" ;
    }

}
