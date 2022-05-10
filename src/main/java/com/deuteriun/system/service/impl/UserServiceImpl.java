package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysRoleCode;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SysUserRole;
import com.deuteriun.system.mapper.SysRoleCodeMapper;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.mapper.SysUserRoleMapper;
import com.deuteriun.system.service.UserService;
import com.deuteriun.system.utils.DateUtils;
import com.deuteriun.system.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static final String SYS_USER_FLAG = "SYS_USER";
    public static final String SYS_ROOT_NAME = "root";
    public static final String SYS_ROLE_TABLE_USER_KEY = "sys_user_id";

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

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
        return sysUserMapper.selectList(new QueryWrapper<SysUser>().eq("del",0));
    }

    @Override
    public List<SysUser> findAllUser(IPage<SysUser> page) {

        IPage<SysUser> sysUserIPage = sysUserMapper.selectPage(page, new QueryWrapper<SysUser>().eq("del",0));
        if (sysUserIPage != null) {
            records = sysUserIPage.getRecords();
            getRoles(records);
        }
        return null;
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

    public static final String SYS_USER_FLAG = "SYS_USER";

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
                List<SysRole> SysRoles = sysUserRoleMapper.selectList(eq);
                if (SysRoles != null) {
                    //Delete previous role data
                    sysUserRoleMapper.deleteBatchIds(SysRoles);
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
            QueryWrapper<SysRole> role_code = new QueryWrapper<SysRole>().eq("role_code", SYS_USER_FLAG);
            SysRole sysRole = sysUserRoleMapper.selectOne(role_code);
            if (sysRole != null) {
                String securityUserName = SecurityUtils.getAuthentication().getName();
                if (securityUserName != null) {
                    SysUser userByName = sysUserMapper.getUserByName(securityUserName);
                    SysUserRole sysUserRole = new SysUserRole()
                            .setRoleId(sysRole.getId())
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
