package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deuteriun.common.enums.ReturnStatus;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysRoleCode;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SysUserRole;
import com.deuteriun.system.mapper.SysRoleCodeMapper;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.mapper.SysUserRoleMapper;
import com.deuteriun.system.security.entity.SecurityUser;
import com.deuteriun.system.service.UserService;
import com.deuteriun.system.utils.DateUtils;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysRoleCodeMapper sysRoleCodeMapper;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public SysUser getUserById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

    @Override
    public SysUser getUserByName(String userName) {
        return sysUserMapper.getUserByName(userName);
    }

    @Override
    public List<SysUser> listAllUsers() {
        return sysUserMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<SysUser> findAllUser(IPage<SysUser> page) {

        IPage<SysUser> sysUserIPage = sysUserMapper.selectPage(page, new QueryWrapper<>());
        if (sysUserIPage != null) {
            return sysUserIPage.getRecords();
        }
        return null;
    }

    @Override
    public List<SysUser> findAllUser(List<String> users, IPage<SysUser> page) {
        IPage<SysUser> usersByNames = sysUserMapper.getUsersByNames(page, users);

        if (usersByNames != null) {
            return usersByNames.getRecords();
        }
        return null;
    }

    public static final String SYS_USER_FLAG = "SYS_USER";

    @Override
    public Boolean add(SysUser user) {
        if (getUserByName(user.getUserName()) != null) {
            try {
                throw new Exception("用户已经存在");
            } catch (Exception ignored) {
            }
        } else if (user.getUserName() != null && StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()))
                    .setBan(false)
                    .setDel(false)
                    .setCreateDate(DateUtils.currentDate())
                    .setModifyDate(DateUtils.currentDate());
            sysUserMapper.insert(user);
            SysUser sysUser = sysUserMapper.getUserByName(user.getUserName());
            QueryWrapper<SysRoleCode> role_code = new QueryWrapper<SysRoleCode>().eq("role_code", SYS_USER_FLAG);
            SysRoleCode sysRoleCode = sysRoleCodeMapper.selectOne(role_code);
            if (sysRoleCode != null) {
                String securityUserName = SecurityUtils.getAuthentication().getName();
                if (securityUserName != null) {
                    SysUser userByName = sysUserMapper.getUserByName(securityUserName);
                    SysUserRole sysUserRole = new SysUserRole()
                            .setRoleId(sysRoleCode.getId())
                            .setSysUserId(sysUser.getId())
                            .setCreateRoleUserId(userByName.getId())
                            .setCreateDate(DateUtils.currentDate());
                    if (sysUserRoleMapper.insert(sysUserRole) > 0) {
                        return true;
                    }
                }
            }
            sysUserMapper.deleteById(sysUser.getId());
        }
        return false;
    }


}
