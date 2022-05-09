package com.deuteriun.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * @ClassName FilesUtils
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 17:35
 * @Version 1.0
 **/
@Component
public class FilesUtils {

    @Value("${deuteriun.files.position}")
    private String filePosition;

    private static String FILE_POSITION;

    @PostConstruct
    public void postConstruct() {
        if (!filePosition.equals("/")) {
            FILE_POSITION = filePosition + "/" + "DeuteriunFiles";
        } else
            FILE_POSITION = System.getProperty("user.dir") + "/" + "DeuteriunFiles";
    }

    public static String getFilePosition() {
        return FILE_POSITION;
    }

    public static void folderCheck() {
        File folder = new File(FilesUtils.getFilePosition());
        folderIniter(folder);
        File year = new File(FilesUtils.getFilePosition() + "/" + DateUtils.currentYearStr());
        folderIniter(year);
        File month = new File(FilesUtils.getFilePosition() + "/" + DateUtils.currentYearStr() + "/" + DateUtils.currentMonthStr());
        folderIniter(month);
        File day = new File(FilesUtils.getFilePosition() + "/" + DateUtils.currentYearStr() + "/" + DateUtils.currentMonthStr() + "/" + DateUtils.currentDayStr());
        folderIniter(day);

    }

    private static void folderIniter(File folder) {
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
    }
}