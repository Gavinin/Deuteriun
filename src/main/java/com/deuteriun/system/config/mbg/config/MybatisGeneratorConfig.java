package com.deuteriun.system.config.mbg.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MybatisGeneratorConfig {
    @Value("${spring.datasource.url}")
    String databaseURL;

    @Value("${spring.datasource.username}")
    String databaseUser;

    @Value("${spring.datasource.password}")
    String databasePassword;

    String author = "Deuteriun";

    String globalProjectPath = "/src/main/java";

    String parentPackageName = "com.deuteriun";

    List<String> tables;

}
