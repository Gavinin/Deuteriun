package com.deuteriun.system.service.impl;

import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysNoticfication;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.mapper.SysNoticficationMapper;
import com.deuteriun.system.service.SysNoticficationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-07-29
 */
@Service
public class SysNoticficationServiceImpl extends ServiceImpl<SysNoticficationMapper, SysNoticfication> implements SysNoticficationService {

    @Resource
    private SysNoticficationMapper sysNoticficationMapper;

    @Override
    public List<SysNoticfication> listByReceiveUserId(SysUser sysUser) {
        if (sysUser.getId() == null) {
            return null;
        }
        return sysNoticficationMapper.listByReceiveUserId(sysUser.getId());
    }
}
