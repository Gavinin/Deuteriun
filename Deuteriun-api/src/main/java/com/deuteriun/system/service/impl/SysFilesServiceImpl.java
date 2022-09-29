package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.common.utils.*;
import com.deuteriun.system.entity.SysFiles;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.exception.FileException;
import com.deuteriun.system.exception.SystemException;
import com.deuteriun.system.mapper.SysFilesMapper;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.service.SysFilesService;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        String name = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
        if (StringUtils.isBlank(name)) {
            throw new SystemException("Can't verify current user.");
        }
        //get user id
        SysUser userByName = sysUserMapper.getUserByName(name);
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
                            .setCreateDate(localDateTime).setDel(false);
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
            List<SysFiles> sysFiles = mixList(file);
            sysFilesList.addAll(sysFiles);
        }
        if (sysFilesList.size() > 0) {
            return fakeDel(sysFilesList) > 0;
        }

        return true;
    }

    @Override
    public SysFiles getFile(SysFiles sysFiles) {
        List<SysFiles> sysFilesList = mixList(sysFiles);
        if (sysFilesList.size() > 0) {
            return sysFilesList.iterator().next();
        }
        return null;
    }

    @Override
    public boolean sendFile(HttpServletRequest request, HttpServletResponse response, SysFiles sysFiles) {
        boolean result = false;
        FileInputStream fin = null;
        FileChannel channel = null;
        try {
            request.setCharacterEncoding("UTF-8");
            File file = new File(sysFiles.getFilePosition());
            fin = new FileInputStream(file);
            long fileLength = file.length();
            //fix chinese file name error code
            String encodedFileName = URLEncoder.encode(sysFiles.getFileName(), StandardCharsets.UTF_8);
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + encodedFileName);//重点
            response.setHeader("Content-Length", String.valueOf(fileLength));
            response.setContentType("application/octet-stream");
            response.setContentType("application/force-download");

            channel = fin.getChannel();
            int buffSize = 1024;
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            byte[] byteArr = new byte[buffSize];
            int nGet = 0;

            while (channel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    nGet = Math.min(buffer.remaining(), buffSize);
                    // read bytes from disk
                    buffer.get(byteArr, 0, nGet);
                    // write bytes to output
                    response.getOutputStream().write(byteArr);
                }
                buffer.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件的关闭放在finally中
        finally {
            if (channel != null) {
                try {
                    channel.close();
                    fin.close();
                    result = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return result;
    }


    public List<SysFiles> mixList(SysFiles sysFile) {
        IPage<SysFiles> sysFilesIPage = new PageUtils<SysFiles>().page(sysFile.getPage(), sysFile.getLimit());
        return sysFilesMapper.mixList(sysFilesIPage, sysFile);
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
