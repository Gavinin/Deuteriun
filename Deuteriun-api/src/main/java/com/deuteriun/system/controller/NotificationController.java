package com.deuteriun.system.controller;


import com.deuteriun.common.utils.Result;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.entity.SysNoticfication;
import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.exception.SystemException;
import com.deuteriun.system.service.SysNoticficationService;
import com.deuteriun.system.service.SysUserService;
import com.deuteriun.system.utils.SecurityUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("apis/notification")
@Api(tags = "Notification Controller", value = "Notification service")
public class NotificationController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysNoticficationService sysNoticficationService;

    @GetMapping("/getnotification")
    Result getNotification() {
        String name = Objects.requireNonNull(SecurityUtils.getAuthentication()).getName();
        if (StringUtils.isBlank(name)) {
            throw new SystemException("Can't find current user.");
        }
        SysUser userByName = sysUserService.getUserByName(name);
        List<SysNoticfication> sysNoticfications = sysNoticficationService.listByReceiveUserId(userByName);
        return Result.success(sysNoticfications);
    }

}
