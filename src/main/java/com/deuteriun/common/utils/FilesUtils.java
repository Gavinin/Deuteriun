package com.deuteriun.common.utils;

import com.deuteriun.system.entity.FilesConf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.time.LocalDateTime;

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

    private static String ROOT_FOLDER_POSITION;

    @PostConstruct
    public void postConstruct() {

        if (!filePosition.equals("/")) {
            ROOT_FOLDER_POSITION = filePosition + "/" + "DeuteriunFiles";
        } else
            ROOT_FOLDER_POSITION = System.getProperty("user.dir") + "/" + "DeuteriunFiles";
    }

    public static String getRootFolderPosition() {
        return ROOT_FOLDER_POSITION;
    }

    public static String getFolderPositionByDate(LocalDateTime localDateTime) {
        return ROOT_FOLDER_POSITION + "/" + localDateTime.getYear() + "/" + localDateTime.getMonthValue() + "/" + localDateTime.getDayOfMonth();
    }

    public static void folderCheck() {
        File folder = new File(FilesUtils.getRootFolderPosition());
        folderIniter(folder);
        File year = new File(FilesUtils.getRootFolderPosition() + "/" + DateUtils.currentDate().getYear());
        folderIniter(year);
        File month = new File(FilesUtils.getRootFolderPosition() + "/" + DateUtils.currentDate().getYear() + "/" + DateUtils.currentDate().getMonthValue());
        folderIniter(month);
        File day = new File(FilesUtils.getRootFolderPosition() + "/" + DateUtils.currentDate().getYear() + "/" + DateUtils.currentDate().getMonthValue() + "/" + DateUtils.currentDate().getDayOfMonth());
        folderIniter(day);

    }

    private static void folderIniter(File folder) {
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
    }

    public static String getSuffix(String originFileName) {
        if (StringUtils.isNotBlank(originFileName)) {
            return originFileName.substring(originFileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    public static Boolean checkSuffix(MultipartFile multipartFile) {
        String suffix = getSuffix(multipartFile.getOriginalFilename());
        if (StringUtils.isNotBlank(suffix)) {
            for (String s : FilesConf.getAllowSuffix()) {
                if (suffix.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }

//    /**
//     * Save File
//     * @return File path
//     */
//    private static String saveFile(File file){
//
//    }


}