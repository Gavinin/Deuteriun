package com.deuteriun.system.config.init;

import com.deuteriun.common.utils.FilesUtils;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @ClassName FolderInitConfig
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 18:36
 * @Version 1.0
 **/
@Configuration
public class FolderInitConfig {

    @PostConstruct
    public void init() {
        FilesUtils.folderCheck();
    }


}
