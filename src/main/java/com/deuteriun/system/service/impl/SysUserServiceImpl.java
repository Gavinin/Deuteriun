package com.deuteriun.system.service.impl;

import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.mapper.SysUserMapper;
import com.deuteriun.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByName(String userName) {
        return sysUserMapper.getUserByName(userName);
    }
}
