package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysNoticfication;
import com.baomidou.mybatisplus.extension.service.IService;
import com.deuteriun.system.entity.SysUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gavinin
 * @since 2022-07-29
 */
public interface SysNoticficationService extends IService<SysNoticfication> {

    List<SysNoticfication> listByReceiveUserId(SysUser sysUser);
}
