package com.deuteriun.system.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {
    public static void main(String[] args) throws XMLParserException, SQLException, IOException, InterruptedException, InvalidConfigurationException {
        generator();
    }

  public static void generator() throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
      List<String> warnings = new ArrayList<>();
      boolean overwrite = true;
      ConfigurationParser cp = new ConfigurationParser(warnings);

      File configFile = new File(System.getProperty("user.dir")+"/src/main/resources/mybatis/generator-configuration.xml");
      Configuration config = cp.parseConfiguration(configFile);
      DefaultShellCallback callback = new DefaultShellCallback(overwrite);
      MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
      myBatisGenerator.generate(null);
  }

}
