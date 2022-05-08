package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deuteriun.common.utils.DateUtils;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.exception.SysException;
import com.deuteriun.system.exception.UserException;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.mapper.SysRoleMapper;
import com.deuteriun.system.service.SysRoleService;
import com.deuteriun.system.service.UserService;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final String SYS_USER_FLAG = "SYS_USER";
    public static final String SYS_ROOT_NAME = "root";
    public static final String SYS_ROLE_TABLE_USER_KEY = "sys_user_id";

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysRoleService sysRoleService;

    @Resource
    SysRoleMapper sysRoleMapper;

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
        List<SysUser> records = null;
        if (sysUserIPage != null) {
            records = sysUserIPage.getRecords();
            getRoles(records);
        }
        return records;
    }

    private void getRoles(List<SysUser> records) {
        List<Long> userList = new ArrayList<>();
        for (SysUser record : records) {
            userList.add(record.getId());
        }
        if (userList.size() > 0) {
            List<SysRole> sysRoles = sysRoleService.listAllByUserIds(userList);
            if (sysRoles.size() > 0) {
                for (SysUser record : records) {
                    List<SysRole> list = new ArrayList<>();
                    Long id = record.getId();
                    for (SysRole sysRole : sysRoles) {
                        if (id.equals(sysRole.getSysUserId())) {
                            list.add(sysRole);
                        }
                    }
                    record.setSysRoleList(list);
                }
            }
        }
    }

    @Override
    public List<SysUser> findAllUser(List<String> users, IPage<SysUser> page) {
        IPage<SysUser> usersByNames = sysUserMapper.getUsersByNames(page, users);
        List<SysUser> records = null;
        if (usersByNames != null) {
            records = usersByNames.getRecords();
            getRoles(records);

        }
        return records;
    }


    @Override
    public Boolean add(SysUser user) {
        if (user.getUserName().equals(SYS_ROOT_NAME)) {
            throw new SysException(SYS_ROOT_NAME + " is system default user, can't be deleted");
        }
        SysUser sysUserInDB = getUserByName(user.getUserName());
        //If the user has been registered
        if (sysUserInDB != null && !sysUserInDB.getDel()) {
            throw new UserException("用户已经存在");
        }
        //If the user had been registered, and it has been deleted
        else if (sysUserInDB != null && sysUserInDB.getDel()) {
            //Delete previous user data
            if (sysUserMapper.deleteById(sysUserInDB) > 0) {
                QueryWrapper<SysRole> eq = new QueryWrapper<SysRole>().eq(SYS_ROLE_TABLE_USER_KEY, sysUserInDB.getId());
                List<SysRole> SysRoles = sysRoleMapper.selectList(eq);
                if (SysRoles != null) {
                    //Delete previous role data
                    sysRoleMapper.deleteBatchIds(SysRoles);
                }
            }
        }
        if (user.getUserName() != null && StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()))
                    .setBan(false)
                    .setDel(false)
                    .setCreateDate(DateUtils.currentDate())
                    .setModifyDate(DateUtils.currentDate());
            sysUserMapper.insert(user);
            SysUser sysUser = sysUserMapper.getUserByName(user.getUserName());


            //Get current authentication user information
            Authentication authentication = SecurityUtils.getAuthentication();
            if (authentication != null) {
                //Get current operate username
                String securityUserName = authentication.getName();
                if (securityUserName != null) {
                    //Find current operate user by username from database
                    SysUser userByName = sysUserMapper.getUserByName(securityUserName);
                    SysRole SysRole = new SysRole()
                            .setRole(SYS_USER_FLAG)
                            .setSysUserId(sysUser.getId())
                            .setCreateRoleUserId(userByName.getId())
                            .setCreateDate(DateUtils.currentDate());
                    if (sysRoleMapper.insert(SysRole) > 0) {
                        return true;
                    }
                }
            }
            sysUserMapper.deleteById(sysUser.getId());
        }
        return false;
    }

    @Override
    public Boolean update(SysUser user) {
        SysUser userByName = sysUserMapper.getUserByName(user.getUserName());
        if (userByName != null) {
            if (!userByName.getDel()) {
                boolean hasChanged = false;
                if (StringUtils.isNotBlank(user.getUserNickName())) {
                    userByName.setUserNickName(user.getUserNickName());
                    hasChanged = true;
                }
                if (StringUtils.isNotBlank(user.getPassword())) {
                    String encodedPassword = passwordEncoder.encode(user.getPassword());
                    userByName.setPassword(encodedPassword);
                    hasChanged = true;
                }
                if (hasChanged) {
                    sysUserMapper.updateById(userByName);
                }
                return true;
            }
        }
        return false;
    }


    @Override
    public Boolean del(SysUser user) {
        SysUser userByName = sysUserMapper.selectById(user.getId());
        if (userByName != null && userByName.getUserName().equals(user.getUserName())) {
            if (passwordEncoder.matches(user.getPassword(), userByName.getPassword())) {
                if (!userByName.getDel()) {
                    userByName.setDel(true);
                    return sysUserMapper.updateById(userByName) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public List<SysRole> getUserRoles() {

        return null;
    }

}
