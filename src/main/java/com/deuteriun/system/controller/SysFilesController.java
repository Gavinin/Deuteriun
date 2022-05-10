package com.deuteriun.system.controller;

import com.deuteriun.common.utils.Result;
import com.deuteriun.system.service.SysFilesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
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

    @PostMapping("/upload")
    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('FILES_UPDATE')")
    public Result fileUploads(HttpServletRequest request, @RequestParam("files") MultipartFile[] file) {
        if (sysFilesService.updateFiles(file)) {
            return Result.success("Update successful");
        }

        return Result.error("Don't update any file");
    }
//
//    @PostMapping("/del")
//    @PreAuthorize(value = "hasAuthority('SYS_USER') AND hasAuthority('FILES_UPDATE')")
//    public Result fileDelete(HttpServletRequest request, @RequestParam("files") String[] files) {
//        if (files!=null)
//        if (sysFilesService.delete(files)) {
//            return Result.success("Update successful");
//        }
//
//        return Result.error("Don't update any file");
//    }
}
