package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.common.utils.DateUtils;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SysUserRole;
import com.deuteriun.system.exception.AuthorException;
import com.deuteriun.system.mapper.SysRoleMapper;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.mapper.SysUserRoleMapper;
import com.deuteriun.system.service.SysUserRoleService;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public static final String ROW_ROLE_FLAG = "role_id";
    public static final String ROW_USER_ID_FLAG = "sys_user_id";

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public List<GrantedAuthority> getGrantedAuthorityListById(Long id) {
        List<SysUserRole> maps = sysUserRoleMapper.listAllByUserId(id);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (SysUserRole sysUserRole : maps) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(sysUserRole.getRoleCode()));
        }
        return grantedAuthorityList;

    }


    @Override
    public List<SysUserRole> listAllByUserIds(List<Long> userList) {
        return sysUserRoleMapper.listAllByUserIds(userList);
    }


    @Override
    public Boolean add(SysUserRole sysUserRole) {
        //Legally user check
        SysUser sysUser = sysUserMapper.selectById(sysUserRole.getSysUserId());
        if (sysUser != null && !sysUser.getDel() && !sysUser.getBan()) {
            //Check if the current user role information already exists
            List<SysUserRole> sysUserRoleList = sysUserRoleMapper.listAllByUserId(sysUserRole.getSysUserId());
            if (sysUserRoleList.size()>0) {
                for (SysUserRole userRole : sysUserRoleList) {
                    if (userRole.getRoleCode().equals(sysUserRole.getRoleCode())){
                        throw new AuthorException("权限已经存在");
                    }
                }
            }
            //Get current operate user info
            String name = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
            SysUser sysUser1 = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", name).eq("del", 0));
            if (sysUser1 != null) {
                sysUserRole.setCreateRoleUserId(sysUser1.getId());
                sysUserRole.setCreateDate(DateUtils.currentDate());
                return sysUserRoleMapper.insert(sysUserRole) > 0;
            }
        }
        return false;
    }
    @Resource
    SysRoleMapper sysRoleMapper;
    @Override
    public Boolean delete(SysUserRole sysUserRole) {
        SysRole sysRole = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("role_code", sysUserRole.getRoleCode()));
        if (sysRole!=null) {
            SysUserRole role = sysUserRoleMapper.selectOne(new QueryWrapper<SysUserRole>().eq(ROW_USER_ID_FLAG, sysUserRole.getSysUserId()).eq(ROW_ROLE_FLAG, sysRole.getId()));
            if (role != null) {
                return sysUserRoleMapper.deleteById(role) > 0;
            }
        }
        return false;
    }


}
