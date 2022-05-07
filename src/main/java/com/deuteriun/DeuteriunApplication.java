package com.deuteriun;

import com.deuteriun.system.utils.LogoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public  class DeuteriunApplication {

    public static void main(String[] args) {

        SpringApplication.run(DeuteriunApplication.class, args);
        LogoUtil.StartLogo();


    }

}
