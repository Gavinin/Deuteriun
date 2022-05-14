package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.common.utils.DateUtils;
import com.deuteriun.common.utils.FilesUtils;
import com.deuteriun.common.utils.UUIDUtils;
import com.deuteriun.system.entity.SysFiles;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.exception.FileException;
import com.deuteriun.system.mapper.SysFilesMapper;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.service.SysFilesService;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
@Service
public class SysFilesServiceImpl extends ServiceImpl<SysFilesMapper, SysFiles> implements SysFilesService {

    @Resource
    SysFilesMapper sysFilesMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public List<SysFiles> updateFiles(MultipartFile[] multipartFiles) {

        //get user id
        SysUser userByName = sysUserMapper.getUserByName(SecurityUtils.getAuthentication().getName());
        if (userByName != null) {
            LocalDateTime localDateTime = DateUtils.currentDate();
            List<SysFiles> sysFilesList = new ArrayList<>();
            String folderPositionByDate = FilesUtils.getFolderPositionByDate(localDateTime);
            for (MultipartFile multipartFile : multipartFiles) {
                if (FilesUtils.checkSuffix(multipartFile)) {
                    SysFiles sysFiles = new SysFiles();
                    String currentFilePosition = folderPositionByDate + "/" + UUIDUtils.getUUIDNonCrossbar();
                    String fileToken = DateUtils.currentDateStr(UUIDUtils.getUUIDNonCrossbar());
                    sysFiles.setFileName(multipartFile.getOriginalFilename())
                            .setFilePosition(currentFilePosition)
                            .setFileToken(fileToken)
                            .setCreateUserId(userByName.getId())
                            .setCreateDate(localDateTime);
                    File file = new File(currentFilePosition);
                    try {
                        multipartFile.transferTo(file);
                    } catch (IOException ignored) {
                        throw new FileException("File update fail");
                    }
                    sysFilesList.add(sysFiles);
                }
            }
            if (sysFilesList.size() > 0) {
                return sysFilesMapper.batchInsert(sysFilesList) > 0 ? sysFilesList : null;
            }
        }
        return null;
    }

    @Override
    public Boolean delete(List<SysFiles> files) {

        List<SysFiles> sysFilesList = new ArrayList<>();
        for (SysFiles file : files) {
            List<SysFiles> sysFiles = sysFilesMapper.mixList(file);
            sysFilesList.addAll(sysFiles);
        }
        if (sysFilesList.size() > 0) {
            return fakeDel(sysFilesList) > 0;
        }

        return true;
    }

    @Override
    public List<SysFiles> download(SysFiles sysFiles) {
        List<SysFiles> sysFilesList = sysFilesMapper.mixList(sysFiles);
        if (sysFilesList.size()>0){

        }
        return null;
    }

    public List<SysFiles> mixList(SysFiles sysFile) {
        return sysFilesMapper.mixList(sysFile);
    }

    public int realDel(List<SysFiles> sysFilesList) {
        return sysFilesMapper.batchDelete(sysFilesList);
    }

    public int fakeDel(List<SysFiles> sysFilesList) {
        for (SysFiles sysFiles : sysFilesList) {
            sysFiles.setDel(true);
        }
        return sysFilesMapper.batchFakeDeleteById(sysFilesList);
    }
}
