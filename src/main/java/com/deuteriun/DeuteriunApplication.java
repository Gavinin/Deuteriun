package com.deuteriun;

import com.deuteriun.system.common.utils.Util;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public  class DeuteriunApplication {

    public static void main(String[] args) {

        SpringApplication.run(DeuteriunApplication.class, args);
        Util.StartLogo();


    }

}
