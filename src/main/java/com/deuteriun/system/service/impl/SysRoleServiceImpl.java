package com.deuteriun.system.service.impl;

import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.mapper.SysRoleMapper;
import com.deuteriun.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Reference
    SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> mixList() {
       return sysRoleMapper.mixList();
    }
}
