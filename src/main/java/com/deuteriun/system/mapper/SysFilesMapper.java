package com.deuteriun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deuteriun.system.entity.SysFiles;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
public interface SysFilesMapper extends BaseMapper<SysFiles> {

    int batchInsert(List<SysFiles> sysFilesList);

    int batchDelete(List<SysFiles> sysFilesList);

    List<SysFiles> mixList(SysFiles sysFile);

    int batchFakeDeleteById(List<SysFiles> sysFilesList);
}
