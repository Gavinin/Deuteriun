package com.deuteriun.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deuteriun.common.enums.DefaultUserStatus;
import com.deuteriun.common.utils.DateUtils;
import com.deuteriun.common.utils.PageUtils;
import com.deuteriun.system.entity.SysFilterRole;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.exception.AuthorException;
import com.deuteriun.system.mapper.SysFilterRoleMapper;
import com.deuteriun.system.service.SysFilterRoleService;
import com.deuteriun.system.service.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-15
 */
@Service
public class SysFilterRoleServiceImpl extends ServiceImpl<SysFilterRoleMapper, SysFilterRole> implements SysFilterRoleService {

    public static final Pattern URL_REG = Pattern.compile("^(/[\\w-./?%&=]*)?$", Pattern.CASE_INSENSITIVE);

    @Resource
    SysFilterRoleMapper sysFilterRoleMapper;

    @Resource
    SysUserService sysUserService;


    @Override
    public List<SysFilterRole> listAllWithRole() {
        return sysFilterRoleMapper.listAllWithRole();

    }

    @Override
    public List<SysFilterRole> mixList(SysFilterRole sysFilterRole) {
        IPage<SysFilterRole> page = new PageUtils<SysFilterRole>().page(sysFilterRole.getPage(), sysFilterRole.getLimit());
        return sysFilterRoleMapper.mixList(page, sysFilterRole);
    }

    @Override
    public boolean add(SysFilterRole sysFilterRole) {
        //if set filter to '/',throw exception
        urlChaeck(sysFilterRole);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            String principal = (String) authentication.getPrincipal();
            SysUser userByName = sysUserService.getUserByName(principal);
            if (userByName != null) {
                sysFilterRole.setCreateUser(userByName.getId());
                sysFilterRole.setCreateDate(DateUtils.currentDate());
                sysFilterRole.setModifyDate(DateUtils.currentDate());
                return sysFilterRoleMapper.insert(sysFilterRole) > 0;
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteById(SysFilterRole sysFilterRole) {
        SysFilterRole sysFileterByDatabase = getById(sysFilterRole.getId());
        if (sysFileterByDatabase != null) {
            SysUser byId = sysUserService.getById(sysFilterRole.getCreateUser());
            if (byId != null) {
                //Default user check,user's filter only can be deleted by oneself.
                for (DefaultUserStatus value : DefaultUserStatus.values()) {
                    if (value.getUserName().equals(byId.getUserName())) {
                        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        SysUser securityUser = sysUserService.getUserByName(principal);
                        if (!securityUser.equals(byId)) {
                            throw new AuthorException("Default filter role only can be delete by oneself");
                        }
                    }
                }
                return sysFilterRoleMapper.deleteById(sysFileterByDatabase) > 0;

            }
        }
        return false;
    }
    private static final String DIAGONAL = "/";

    private void urlChaeck(SysFilterRole sysFilterRole) {
        if (DIAGONAL.equals(sysFilterRole.getFilter())) {
            throw new AuthorException("root adress should not be change");
        }
        if (URL_REG.matcher(sysFilterRole.getFilter()).find()) {
            throw new AuthorException("address is illegal");
        }
    }
}
