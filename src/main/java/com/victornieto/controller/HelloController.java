package com.victornieto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;

/**
 * Created by victor.nieto.castan on 29/03/2017.
 */
@RestController
@RequestMapping("/api/v1")
public class HelloController {

    @GetMapping("/hello")
    public String helloWorld() throws UnknownHostException {
    }

    @GetMapping("/bye")
    public String byeWorld() { return "Bye World!!" ; }

}
