package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.system.entity.SysUserRole;
import com.deuteriun.system.mapper.SysUserRoleMapper;
import com.deuteriun.system.service.SysUserRoleService;
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

    @Override
    public List<GrantedAuthority> getGrantedAuthorityListById(Long id) {
        List<SysUserRole> maps = sysUserRoleMapper.listAllByUserId(id);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (SysUserRole sysUserRoleDetail : maps) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(sysUserRoleDetail.getRoleCode()));
        }
        return grantedAuthorityList;

    }

    @Override
    public List<SysUserRole> listAllByUserIds(List<Long> userList) {
      return   sysUserRoleMapper.listAllByUserIds(userList);
    }

    @Override
    public Boolean add(SysUserRole sysUserRole) {
        return null;
    }

    @Override
    public Boolean delete(SysUserRole sysUserRole) {
        return null;
    }


}
