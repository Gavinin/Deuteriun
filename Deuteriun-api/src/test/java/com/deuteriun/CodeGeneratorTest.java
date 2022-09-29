package com.deuteriun;

import com.deuteriun.system.config.mbg.MybatisGenerator;
import com.deuteriun.system.config.mbg.config.MybatisGeneratorConfig;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("dev")
class CodeGeneratorTest {

    @Test
    public void codeCreator() {
        MybatisGeneratorConfig mybatisGeneratorConfig = new MybatisGeneratorConfig();
        //Author
        mybatisGeneratorConfig.setAuthor("");
        mybatisGeneratorConfig.setDatabaseUrl("");
        mybatisGeneratorConfig.setDatabaseUser("");
        mybatisGeneratorConfig.setDatabasePassword("");
        mybatisGeneratorConfig.setParentPackageName("com.deuteriun.system");
        mybatisGeneratorConfig.setTables(Arrays.asList("sys_noticfication".split(",")));

        new MybatisGenerator().generatorCode(mybatisGeneratorConfig);

    }
}
