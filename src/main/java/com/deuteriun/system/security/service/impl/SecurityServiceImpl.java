package com.deuteriun.system.security.service.impl;

import com.deuteriun.system.dao.SysUserMapper;
import com.deuteriun.system.dao.SysUserRoleMapper;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SysUserRoleDetail;
import com.deuteriun.system.security.entity.UserDo;
import com.deuteriun.system.security.service.SecurityService;
import com.deuteriun.system.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            throw new UsernameNotFoundException("Not found user");
        }
        UserDo userDo = new UserDo();
        userDo.setName(userByName.getNameId());
        userDo.setPassword(userByName.getPassword());
        userDo.setGrantedAuthorityList(getGrantedAuthorityListById(userByName.getId()));

        return userDo;
    }

    @Override
    public List<GrantedAuthority> getGrantedAuthorityListById(Long id) {
        List<SysUserRoleDetail> maps = sysUserRoleMapper.listAllByUserId(id);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (SysUserRoleDetail sysUserRoleDetail : maps) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(sysUserRoleDetail.getRoleCode()));
        }
        return grantedAuthorityList;
    }
}
