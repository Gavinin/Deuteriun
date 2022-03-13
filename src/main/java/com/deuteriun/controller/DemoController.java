package com.deuteriun.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    /**
     * 登录成功的主页返回值
     *
     * @return
     */
    @PostMapping("welcome")
    @PreAuthorize("hasAuthority('sys:select')")
    public String welcome() {
        return "欢迎来到主页";
    }

    /**
     * 登录失败的返回值
     *
     * @return
     */
    @PostMapping("fail")
    @PreAuthorize("hasAuthority('sys:select')")
    public String fail() {
        return "登录失败了";
    }

    /**
     * 开启方法权限的注解
     *
     * @return
     */
    @GetMapping("add")
    @PreAuthorize("hasAuthority('sys:select')")
    public String add() {
        return "欢迎来到主ADD";
    }

    @GetMapping("update")
    @PreAuthorize("hasAuthority('sys:update')")
    public String update() {
        return "欢迎来到UPDATE";
    }

    @GetMapping("delete")
    @PreAuthorize("hasAuthority('sys:del')")
    public String delete() {
        return "欢迎来到DELETE";
    }

    @GetMapping("select")
    @PreAuthorize("hasAuthority('sys:select')")
    public String select() {
        return "欢迎来到SELECT";
    }

    @GetMapping("role")
    public String role() {
        return "欢迎来到ROLE";
    }

    @GetMapping("admin/hello")
    @PreAuthorize("hasAuthority('sys:admin')")
    public String admin() {
        return "我是只有 admin 角色才可以访问的";
    }

}



