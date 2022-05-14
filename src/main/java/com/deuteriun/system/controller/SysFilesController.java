package com.deuteriun.system.controller;

import com.deuteriun.common.utils.Result;
import com.deuteriun.system.entity.SysFiles;
import com.deuteriun.system.service.SysFilesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * File System
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
@RestController
@RequestMapping("/files")
public class SysFilesController {

    @Resource
    SysFilesService sysFilesService;


    @GetMapping("/list")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('FILES_UPDATE')")
    public Result fileList(HttpServletRequest request, @Validated @RequestBody SysFiles sysFiles) {
        List<SysFiles> sysFiles1 = sysFilesService.mixList(sysFiles);
        if (sysFiles1.size() > 0) {
            return Result.success(sysFiles1);
        }
        return Result.error("Don't have any file");
    }

    @GetMapping("/download")
    @PreAuthorize(value = "hasAuthority('SYS_USER')")
    public Result getFile(HttpServletRequest request, @Valid @RequestBody SysFiles sysFiles) {

        List<SysFiles> sysFiles1 = sysFilesService.download(sysFiles);
        if (sysFiles1.size() > 0) {
            return Result.success(sysFiles1);
        }
        return Result.error("Don't have any file");
    }


    @PutMapping("/upload")
    @PreAuthorize(value = "hasAnyAuthority('SYS_USER') ")
    public Result fileUploads(HttpServletRequest request, @RequestParam("files") MultipartFile[] file) {
        if (sysFilesService.updateFiles(file).size() > 0) {
            return Result.success("Update successful");
        }
        return Result.error("Don't update any file");
    }

    @DeleteMapping("/del")
    @PreAuthorize(value = "hasAuthority('FILES_MANAGE')")
    public Result fileDelete(HttpServletRequest request, @RequestParam("files") List<SysFiles> files) {
        if (files.size() > 0) {
            if (sysFilesService.delete(files)) {
                return Result.success("Delete successful");
            }
        }
        return Result.error("Don't update any file");
    }
}
