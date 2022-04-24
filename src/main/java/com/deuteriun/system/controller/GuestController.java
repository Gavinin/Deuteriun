package com.deuteriun.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@Api(tags = "Guest",value = "Guest Controller")
public class GuestController {

    @ApiOperation("GSC")
    @PostMapping("/gc")
    public String gc(){

        return "success";
    }
}

