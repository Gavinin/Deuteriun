package com.deuteriun.system.mbg;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.deuteriun.system.mbg.config.MybatisGeneratorConfig;
import org.springframework.stereotype.Component;

@Component
public class MybatisGenerator {

    private final String projectPath = System.getProperty("user.dir");
    // 数据源配置


    public void generatorCode (MybatisGeneratorConfig mybatisGeneratorConfig) {
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(mybatisGeneratorConfig.getDatabaseURL(), mybatisGeneratorConfig.getDatabaseUser(), mybatisGeneratorConfig.getDatabasePassword())
                .typeConvert(new MySqlTypeConvert());
        FastAutoGenerator
                .create(dataSourceConfig)
                //全局配置
                .globalConfig(builder -> {
                    builder.author(mybatisGeneratorConfig.getAuthor()) // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir() // 执行完毕不打开文件夹
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + mybatisGeneratorConfig.getGlobalProjectPath()); // 指定输出目录
                })
                //包配置
                .packageConfig(builder -> {
                    builder.parent(mybatisGeneratorConfig.getParentPackageName())
                            .controller("controller")
                            .entity("entity")
                            .service("service")
                            .mapper("mapper");
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(mybatisGeneratorConfig.getTables()) // 设置需要生成的表名
                            .addTablePrefix("")// 设置过滤表前缀
                            .serviceBuilder() //开启service策略配置
                            .formatServiceFileName("%sService") //取消Service前的I
                            .controllerBuilder() //开启controller策略配置
                            .enableRestStyle() //配置restful风格
                            .enableHyphenStyle() //url中驼峰转连字符
                            .entityBuilder() //开启实体类配置
                            .enableLombok() //开启lombok
                            .enableChainModel(); //开启lombok链式操作

                })
                //模板配置
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                //执行
                .execute();
    }
}

