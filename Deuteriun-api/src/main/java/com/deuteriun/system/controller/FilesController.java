package com.deuteriun.system.controller;

import com.deuteriun.common.utils.Result;
import com.deuteriun.system.entity.SysFiles;
import com.deuteriun.system.service.SysFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
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
@Api(tags = "File Controller", value = "System manage controller")
public class FilesController {

    @Resource
    SysFilesService sysFilesService;


    @GetMapping("/list")
    @ApiOperation("sysFiles:SysFiles ,should include page and limit")
    Result fileList(HttpServletRequest request, @Validated @RequestBody SysFiles sysFiles) {
        List<SysFiles> sysFiles1 = sysFilesService.mixList(sysFiles);
        if (sysFiles1.size() > 0) {

            return Result.success(sysFiles1);
        }
        return Result.error("Don't have any file");
    }

    @GetMapping("/download")
    @ApiOperation("sysFiles:SysFiles")
    void getFile(HttpServletRequest request, HttpServletResponse response, @Valid @RequestBody SysFiles sysFiles) throws IOException {
        SysFiles sysFiles1 = sysFilesService.getFile(sysFiles);
        if (sysFiles1 != null) {
            if (!sysFilesService.sendFile(request, response, sysFiles1)) {
                response.getWriter().println("File download fail!!");
            }
        }
    }


    @PutMapping("/upload")
    @ApiOperation("MultipartFile[] file")
    Result fileUploads(HttpServletRequest request, @RequestParam("files") MultipartFile[] file) {
        if (sysFilesService.updateFiles(file).size() > 0) {
            return Result.success("Update successful");
        }
        return Result.error("Don't update any file");
    }

    @DeleteMapping("/del")
    @ApiOperation("files: List<SysFiles>")
    Result fileDelete(HttpServletRequest request, @RequestParam("files") List<SysFiles> files) {
        if (files.size() > 0) {
            if (sysFilesService.delete(files)) {
                return Result.success("Delete successful");
            }
        }
        return Result.error("Don't update any file");
    }
}
