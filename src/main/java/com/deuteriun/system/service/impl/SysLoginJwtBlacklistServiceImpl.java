package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.system.entity.SysLoginJwtBlacklist;
import com.deuteriun.system.mapper.SysLoginJwtBlacklistMapper;
import com.deuteriun.system.service.SysLoginJwtBlacklistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-01
 */
@Service
public class SysLoginJwtBlacklistServiceImpl extends ServiceImpl<SysLoginJwtBlacklistMapper, SysLoginJwtBlacklist> implements SysLoginJwtBlacklistService {

    @Resource
    SysLoginJwtBlacklistMapper sysLoginJwtBlacklistMapper;

    @Override
    public SysLoginJwtBlacklist listByuserNameAndToken(String userName, String token) {
        return sysLoginJwtBlacklistMapper.listByuserNameAndToken(userName,token);
    }

    @Override
    public List<SysLoginJwtBlacklist> listByuserName(String userName) {
        return sysLoginJwtBlacklistMapper.listByuserName(userName);
    }

    @Override
    public Boolean delete(String userName) {
        return sysLoginJwtBlacklistMapper.deleteByUserName(userName);
    }
}
