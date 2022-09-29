package com.deuteriun.system.mapper;

import com.deuteriun.system.entity.SysNoticfication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Gavinin
 * @since 2022-07-29
 */
public interface SysNoticficationMapper extends BaseMapper<SysNoticfication> {
    List<SysNoticfication> listByReceiveUserId(Long userId);
}
