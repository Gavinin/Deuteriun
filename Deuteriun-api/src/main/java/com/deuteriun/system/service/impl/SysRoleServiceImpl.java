package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.common.enums.DefaultRolesStatus;
import com.deuteriun.common.utils.PageUtils;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.exception.SystemException;
import com.deuteriun.system.mapper.SysRoleMapper;
import com.deuteriun.system.service.SysRoleService;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
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
    public List<SysRole> mixList(SysRole sysRole) {
        IPage<SysRole> sysRolePage = new PageUtils<SysRole>().page(sysRole.getPage(), sysRole.getLimit());
        List<SysRole> sysRoles;
        sysRoles = sysRoleMapper.mixList(sysRolePage, sysRole);
        return sysRoles;
    }

    @Override
    public boolean add(SysRole sysRole) {
        defaultRoleChecker(sysRole);
        return sysRoleMapper.insert(sysRole) > 0;

    }

    @Override
    public boolean delete(SysRole sysRole) {
        SysRole sysRoleServiceById = getById(sysRole.getId());
        defaultRoleChecker(sysRoleServiceById);
        return sysRoleMapper.deleteById(sysRole) > 0;
    }

    private void defaultRoleChecker(SysRole sysRole) {
        for (DefaultRolesStatus value : DefaultRolesStatus.values()) {
            if (value.getRoleCode().equals(sysRole.getRoleCode())) {
                throw new SystemException("Default role can't be changed!!");
            }
        }
    }
}
