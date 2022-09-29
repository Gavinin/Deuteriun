package com.deuteriun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deuteriun.system.entity.SysFiles;
import com.deuteriun.system.entity.SysRole;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
public interface SysFilesMapper extends BaseMapper<SysFiles> {
    /**
     * save multiply files
     * @param sysFilesList file list
     * @return finish number
     */
    int batchInsert(List<SysFiles> sysFilesList);
    /**
     * delete multiply files
     * @param sysFilesList file list
     * @return finish
     */
    int batchDelete(List<SysFiles> sysFilesList);

    /**
     * list with multiply case and page
     * @param page Page class page and limit
     * @param sysFile case
     * @return sys files list
     */
    List<SysFiles> mixList(IPage<SysFiles> page, SysFiles sysFile);

    /**
     * fake delete multiply files
     * @param sysFilesList file list
     * @return finish
     */
    int batchFakeDeleteById(List<SysFiles> sysFilesList);
}
