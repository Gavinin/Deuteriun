package com.deuteriun;

import com.deuteriun.system.mbg.MybatisGenerator;
import com.deuteriun.system.mbg.config.MybatisGeneratorConfig;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CodeGeneratorTest.class})
@EnableAutoConfiguration
public class CodeGeneratorTest {


    public static void main(String[] args) {
        MybatisGeneratorConfig mybatisGeneratorConfig = new MybatisGeneratorConfig();
        mybatisGeneratorConfig.setAuthor("Gavinin");
        mybatisGeneratorConfig.setDatabaseURL("jdbc:mysql://dev.gvw.asia:8836/deuteriun?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&openSSL=false");
        mybatisGeneratorConfig.setDatabaseUser("myutils");
        mybatisGeneratorConfig.setDatabasePassword("Myutils&084282");
        mybatisGeneratorConfig.setParentPackageName("com.deuteriun.system");
        mybatisGeneratorConfig.setTables(Arrays.asList("sys_user,sys_user_role,sys_role_code,sys_log".split(",")));

        new MybatisGenerator().generatorCode(mybatisGeneratorConfig);

    }
}
