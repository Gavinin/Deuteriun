package com.deuteriun.system.task;

import com.deuteriun.common.utils.FilesUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName CreateFolderTask
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 18:49
 * @Version 1.0
 **/
@Component
public class CreateFolderTask {

    @Scheduled(cron = "0 0 0 * * ?")
    public void start() {
        FilesUtils.folderCheck();
    }

}
