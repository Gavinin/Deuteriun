package com.deuteriun.system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @PostMapping("/error")
    public String error(){
        return "Wrong URL";

    }

}
