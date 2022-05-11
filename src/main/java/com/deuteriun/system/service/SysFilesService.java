package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysFiles;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
public interface SysFilesService extends IService<SysFiles> {

    Boolean updateFiles(MultipartFile[] file);

    Boolean delete(List<SysFiles> files);

    List<SysFiles> mixList(SysFiles sysFile);
}
