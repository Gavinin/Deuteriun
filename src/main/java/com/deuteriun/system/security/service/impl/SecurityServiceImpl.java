package com.deuteriun.system.security.service.impl;

import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SysUserRoleDTO;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.mapper.SysUserRoleMapper;
import com.deuteriun.system.security.entity.UserDo;
import com.deuteriun.system.security.service.SecurityService;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;


    @Override
    public UserDo getUserDetailByName(String userName) {
        SysUser userByName = sysUserMapper.getUserByName(userName);
        if (userByName==null){
            throw new UsernameNotFoundException("");
        }else if (userByName.getBan()){
            throw new LockedException("");
        }else if (userByName.getDel()){
            throw new DisabledException("");
        }
        UserDo userDo = new UserDo();
        userDo.setName(userByName.getNameId());
        userDo.setPassword(userByName.getPassword());
        userDo.setGrantedAuthorityList(getGrantedAuthorityListById(userByName.getId()));

        return userDo;
    }

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
