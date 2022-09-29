package com.deuteriun.system.startup;

import com.deuteriun.common.utils.FilesUtils;
import com.deuteriun.system.service.AutoStartService;
import org.springframework.stereotype.Component;

/**
 * @ClassName FolderInitConfigImpl
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 18:36
 * @Version 1.0
 **/
@Component
public class FolderInitConfigImpl implements AutoStartService {

    @Override
    public void init() {
        FilesUtils.folderCheck();
    }


}
