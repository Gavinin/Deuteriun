package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deuteriun.common.utils.DateUtils;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.exception.AuthorException;
import com.deuteriun.system.mapper.SysRoleMapper;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    public static final String ROW_ROLE_FLAG = "role";
    public static final String ROW_USER_ID_FLAG = "sys_user_id";

    @Resource
    SysRoleMapper sysRoleMapper;

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public List<GrantedAuthority> getGrantedAuthorityListById(Long id) {
        List<SysRole> maps = sysRoleMapper.selectList(new QueryWrapper<SysRole>().eq("sys_user_id", id));
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (SysRole sysRole : maps) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(sysRole.getRole()));
        }
        return grantedAuthorityList;

    }

    @Override
    public List<SysRole> listAllByUserIds(List<Long> userList) {
        return sysRoleMapper.selectList(new QueryWrapper<SysRole>().in("sys_user_id", userList));

    }

    @Override
    public boolean add(SysRole sysRole) {
        //Legally user check
        SysUser sysUser = sysUserMapper.selectById(sysRole.getSysUserId());
        if (sysUser != null && !sysUser.getDel() && !sysUser.getBan()) {
            //Check if the current user role information already exists
            SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq(ROW_USER_ID_FLAG, sysRole.getSysUserId()).eq(ROW_ROLE_FLAG, sysRole.getRole()));
            if (role != null) {
                throw new AuthorException("权限已经存在");
            }
            //Check if the authority is in the authority list
            Collection<SimpleGrantedAuthority> authorities = SecurityUtils.getAllAuthorities();
            boolean inAuthList = false;
            for (SimpleGrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(sysRole.getRole())) {
                    inAuthList = true;
                    break;
                }
            }
            if (inAuthList) {
                //Get current operate user info
                String name = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
                SysUser sysUser1 = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", name).eq("del",0));
                if (sysUser1 != null) {
                    sysRole.setCreateRoleUserId(sysUser1.getId());
                    sysRole.setCreateDate(DateUtils.currentDate());
                    return sysRoleMapper.insert(sysRole) > 0;
                }
            }

        }

        return false;
    }

    @Override
    public Boolean delete(SysRole sysRole) {
        SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq(ROW_USER_ID_FLAG, sysRole.getSysUserId()).eq(ROW_ROLE_FLAG, sysRole.getRole()));
        if (role != null) {
            return sysRoleMapper.deleteById(role) > 0;
        }
        return false;
    }

}
