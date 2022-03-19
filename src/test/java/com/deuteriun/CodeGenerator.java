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
@SpringBootTest(classes = {CodeGenerator.class})
@EnableAutoConfiguration
public class CodeGenerator {


    public static void main(String[] args) {
        MybatisGeneratorConfig mybatisGeneratorConfig = new MybatisGeneratorConfig();
        mybatisGeneratorConfig.setAuthor("Gavinin");
        mybatisGeneratorConfig.setDatabaseURL("jdbc:mysql://localhost:3306/deuteriun?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&openSSL=false");
        mybatisGeneratorConfig.setDatabaseUser("deuteriun");
        mybatisGeneratorConfig.setDatabasePassword("Dt#842196");
        mybatisGeneratorConfig.setParentPackageName("com.deuteriun.system");
        mybatisGeneratorConfig.setTables(Arrays.asList("sys_user,sys_role,sys_user_role,sys_log".split(",")));

        new MybatisGenerator().generatorCode(mybatisGeneratorConfig);

    }
}
