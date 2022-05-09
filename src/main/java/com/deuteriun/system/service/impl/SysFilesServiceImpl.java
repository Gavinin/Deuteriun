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
    public Boolean updateFiles(MultipartFile[] multipartFiles) {
        LocalDateTime localDateTime = DateUtils.currentDate();
        List<SysFiles> sysFilesList = new ArrayList<>();
        String folderPositionByDate = FilesUtils.getFolderPositionByDate(localDateTime);
        SysUser userByName = sysUserMapper.getUserByName(SecurityUtils.getAuthentication().getName());
        if (userByName != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                if (FilesUtils.checkSuffix(multipartFile)) {
                    SysFiles sysFiles = new SysFiles();
                    String currentFilePosition = folderPositionByDate + "/" + UUIDUtils.getUUIDNonCrossbar();

                    sysFiles.setFileName(multipartFile.getOriginalFilename())
                            .setFilePosition(currentFilePosition)
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
                return sysFilesMapper.batchInsert(sysFilesList) > 0;
            }
        }
        return false;
    }
}
