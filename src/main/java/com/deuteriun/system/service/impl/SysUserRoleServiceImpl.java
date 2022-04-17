package com.deuteriun.system.service.impl;

import com.deuteriun.system.entity.SysUserRole;
import com.deuteriun.system.entity.SysUserRoleDTO;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.mapper.SysUserRoleMapper;
import com.deuteriun.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Resource
    SysUserMapper s;

    @Override
    public List<GrantedAuthority> getGrantedAuthorityListById(Long id) {
        List<SysUserRoleDTO> maps = sysUserRoleMapper.listAllByUserId(id);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (SysUserRoleDTO sysUserRoleDetail : maps) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(sysUserRoleDetail.getRoleCode()));
        }
        return grantedAuthorityList;

    }

}
