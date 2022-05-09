package com.deuteriun.system.mapper;

import com.deuteriun.system.entity.SysFiles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
}
