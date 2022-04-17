package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.service.UserService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Resource
    SysUserMapper sysUserMapper;

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
    public List<SysUser> findAllUser(String index) {
        List<String> strings = Arrays.asList(index.split(","));
        return findAllUser(strings);
    }

    @Override
    public List<SysUser> findAllUser(List<String> indexList) {
        return sysUserMapper.getUsersByNames(indexList);
    }
}
