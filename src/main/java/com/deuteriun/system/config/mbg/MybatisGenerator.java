package com.deuteriun.system.config.mbg;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.deuteriun.system.config.mbg.config.MybatisGeneratorConfig;
import org.springframework.stereotype.Component;

@Component
public class MybatisGenerator {

    private final String projectPath = System.getProperty("user.dir");

    public void generatorCode(MybatisGeneratorConfig mybatisGeneratorConfig) {
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(mybatisGeneratorConfig.getDatabaseURL(), mybatisGeneratorConfig.getDatabaseUser(), mybatisGeneratorConfig.getDatabasePassword())
                .typeConvert(new MySqlTypeConvert());
        FastAutoGenerator
                .create(dataSourceConfig)
                //全局配置
                .globalConfig(builder -> {
                    // 设置作者
                    builder.author(mybatisGeneratorConfig.getAuthor())
                            // 开启 swagger 模式
                            // .enableSwagger()
                            // 执行完毕不打开文件夹
                            .disableOpenDir()
                            // 覆盖已生成文件
                            .fileOverride()
                            // 指定输出目录
                            .outputDir(projectPath + mybatisGeneratorConfig.getGlobalProjectPath());
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
                    // 设置需要生成的表名
                    builder.addInclude(mybatisGeneratorConfig.getTables())
                            // 设置过滤表前缀
                            .addTablePrefix("")
                            //开启service策略配置
                            .serviceBuilder()
                            //取消Service前的I
                            .formatServiceFileName("%sService")
                            //开启controller策略配置
                            .controllerBuilder()
                            //配置restful风格
                            .enableRestStyle()
                            //url中驼峰转连字符
                            .enableHyphenStyle()
                            //开启实体类配置
                            .entityBuilder()
                            //开启lombok
                            .enableLombok()
                            //开启lombok链式操作
                            .enableChainModel();

                })
                //模板配置
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                //执行
                .execute();
    }
}

