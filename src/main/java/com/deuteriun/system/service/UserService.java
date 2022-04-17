package com.deuteriun.system.service;

import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.mapper.SysUserMapper;

import javax.annotation.Resource;
import java.util.List;

public interface UserService {

    SysUser getUserById(Long userId);

    SysUser getUserByName(String userName);

    List<SysUser> listAllUsers();

    List<SysUser> findAllUser(String index);

    List<SysUser> findAllUser(List<String> indexList);




}
