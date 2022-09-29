package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.common.utils.DateUtils;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysRole;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.entity.SysUserRole;
import com.deuteriun.system.exception.SystemException;
import com.deuteriun.system.exception.UserException;
import com.deuteriun.system.mapper.SysRoleMapper;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.mapper.SysUserRoleMapper;
import com.deuteriun.system.service.SysUserRoleService;
import com.deuteriun.system.service.SysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gavinin
 * @since 2022-03-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    public static final String SYS_USER_FLAG = "SYS_USER";
    public static final String SYS_ROOT_NAME = "root";
    public static final Long ANONYMOUS_CREATE_USER_ID = -1L;
    public static final String SYS_USER_ROLE_TABLE_USER_KEY = "sys_user_id";

    public static final String HAS_DELETE_FLAG = "del";

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysUserRoleService sysUserRoleService;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

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
    public void getUserWithRoles(SysUser user) {
       user.setSysUserRoleList( sysUserRoleService.listAllByUserId(user.getId()));
    }

    @Override
    public List<SysUser> listAllUsers() {
        return sysUserMapper.selectList(new QueryWrapper<SysUser>().eq(HAS_DELETE_FLAG, 0));
    }

    @Override
    public List<SysUser> getAllUserWithRoles(IPage<SysUser> page) {

        IPage<SysUser> sysUserPage = sysUserMapper.selectPage(page, new QueryWrapper<SysUser>().eq(HAS_DELETE_FLAG, 0));
        if (sysUserPage != null) {
            List<SysUser> records = sysUserPage.getRecords();
            listUserWithRoles(records);
            return records;
        }
        return null;
    }

    private void listUserWithRoles(List<SysUser> userLists) {
        List<Long> userList = new ArrayList<>();
        //get all id from list
        for (SysUser record : userLists) {
            userList.add(record.getId());
        }
        if (userList.size() > 0) {
            //get all sys role
            List<SysUserRole> sysUserRoles = sysUserRoleService.listAllByUserIds(userList);
            if (sysUserRoles.size() > 0) {
                for (SysUser sysUser : userLists) {
                    List<SysUserRole> list = new ArrayList<>();
                    Long id = sysUser.getId();
                    for (SysUserRole sysUserRole : sysUserRoles) {
                        if (id.equals(sysUserRole.getSysUserId())) {
                            list.add(sysUserRole);
                        }
                    }
                    sysUser.setSysUserRoleList(list);
                }
            }
        }
    }

    @Override
    public List<SysUser> getAllUserWithRoles(List<String> users, IPage<SysUser> page) {
        IPage<SysUser> usersByNames = sysUserMapper.getUsersByNames(page, users);
        List<SysUser> records = null;
        if (usersByNames != null) {
            records = usersByNames.getRecords();
            listUserWithRoles(records);

        }
        return records;
    }


    @Override
    public SysUser add(SysUser user) {
        if (user.getUserName().equals(SYS_ROOT_NAME)) {
            throw new SystemException(SYS_ROOT_NAME + " is system default user, can't be deleted");
        }
        SysUser sysUserInDatebase = getUserByName(user.getUserName());
        //If the user has been registered
        if (sysUserInDatebase != null && !sysUserInDatebase.getDel()) {
            throw new UserException("用户已经存在");
        }
        //If the user had been registered, and it has been deleted
        else if (sysUserInDatebase != null && sysUserInDatebase.getDel()) {
            //Delete previous user data
            if (sysUserMapper.deleteById(sysUserInDatebase) > 0) {
                QueryWrapper<SysUserRole> eq = new QueryWrapper<SysUserRole>().eq(SYS_USER_ROLE_TABLE_USER_KEY, sysUserInDatebase.getId());
                List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(eq);
                if (sysUserRoleList.size() > 0) {
                    //Delete previous role data
                    sysUserRoleMapper.deleteBatchIds(sysUserRoleList);
                }
            }
        }
        if (user.getUserName() != null && StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()))
                    .setBan(false)
                    .setDel(false)
                    .setCreateDate(DateUtils.currentDate())
                    .setModifyDate(DateUtils.currentDate());
            //insert user into db
            int insert = sysUserMapper.insert(user);
            SysUser sysUser = sysUserMapper.getUserByName(user.getUserName());
            if (insert > 0) {
                QueryWrapper<SysRole> roleCode = new QueryWrapper<SysRole>().eq("role_code", SYS_USER_FLAG);
                SysRole sysRole = sysRoleMapper.selectOne(roleCode);
                if (sysRole != null) {
                    //get user id who inserted
                    SysUserRole inserter = new SysUserRole()
                            .setRoleId(sysRole.getId())
                            .setSysUserId(sysUser.getId())
                            //user can register by stranger,so CreateRoleUser is null :-1
                            .setCreateRoleUserId(ANONYMOUS_CREATE_USER_ID)
                            .setCreateDate(DateUtils.currentDate());
                    if (sysUserRoleMapper.insert(inserter) > 0) {
                        List<SysUserRole> sysUserRoles = sysUserRoleService.listAllByUserId(sysUser.getId());
                        if (sysUserRoles.size() <= 0) {
                            log.warn(sysUser.getUserName() + " has no authority information");
                        }
                        sysUser.setSysUserRoleList(sysUserRoles);
                        String encodePassword = passwordEncoder.encode(user.getPassword());
                        sysUser.setPassword(encodePassword);
                        return sysUser;
                    }
                }
            }
            sysUserMapper.deleteById(sysUser.getId());
        }
        return null;
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
                    if (sysUserMapper.updateById(userByName) > 0) {

                    }
                }
            }
        }
        return false;
    }
}
