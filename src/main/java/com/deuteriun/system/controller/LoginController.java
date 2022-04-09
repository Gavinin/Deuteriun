package com.deuteriun.system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping()
@RestController
public class LoginController {

    @PostMapping
    public String login(){

        return "success";
    }
}
