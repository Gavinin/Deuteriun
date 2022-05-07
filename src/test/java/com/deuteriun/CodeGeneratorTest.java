package com.deuteriun;

import com.deuteriun.system.config.mbg.MybatisGenerator;
import com.deuteriun.system.config.mbg.config.MybatisGeneratorConfig;


import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CodeGeneratorTest.class})
@EnableAutoConfiguration
public class CodeGeneratorTest {


    public static void main(String[] args) {
        MybatisGeneratorConfig mybatisGeneratorConfig = new MybatisGeneratorConfig();
        mybatisGeneratorConfig.setAuthor("Gavinin");
        mybatisGeneratorConfig.setDatabaseURL("jdbc:mysql://localhost:3306/deuteriun?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&openSSL=false");
        mybatisGeneratorConfig.setDatabaseUser("root");
        mybatisGeneratorConfig.setDatabasePassword("Gavin&084282");
        mybatisGeneratorConfig.setParentPackageName("com.deuteriun.system");
        mybatisGeneratorConfig.setTables(Arrays.asList("sys_login_jwt_blacklist".split(",")));

        new MybatisGenerator().generatorCode(mybatisGeneratorConfig);

    }
}
