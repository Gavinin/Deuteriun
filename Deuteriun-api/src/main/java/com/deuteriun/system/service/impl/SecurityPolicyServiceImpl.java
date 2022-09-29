package com.deuteriun.system.service.impl;

import com.deuteriun.common.utils.IpUtils;
import com.deuteriun.common.utils.StringUtils;
import com.deuteriun.system.cache.CacheService;
import com.deuteriun.system.exception.AuthorException;
import com.deuteriun.system.exception.SystemException;
import com.deuteriun.system.service.SecurityPolicyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName SecurityPolicyServiceImpl
 * @Description TODO
 * @Author gavin
 * @Date 15/6/2022 18:24
 * @Version 1.0
 **/
@Service
public class SecurityPolicyServiceImpl implements SecurityPolicyService {

    @Resource(name = "Redis2")
    CacheService ipBlackListRedis;

    @Override
    public void ipBlockor(String serviceName, HttpServletRequest request, Integer limit) {
        String ipStr = IpUtils.getIpAddr(request);
        if (StringUtils.isNoneBlank(ipStr)) {
            String ipKey = serviceName + "_" + String.valueOf(IpUtils.ip2Long(ipStr));
            String ipTimes = ipBlackListRedis.get(ipKey);
            if (StringUtils.isBlank(ipTimes)) {
                ipTimes = "0";
            }
            long aLong = Long.parseLong(ipTimes);
            if (aLong > limit) {
                throw new AuthorException("IP请求次数过多，请稍后再试");
            }
            aLong++;
            ipTimes = String.valueOf(aLong);
            ipBlackListRedis.expire(ipKey, ipTimes);
        } else {

            throw new SystemException("没有IP");
        }
    }

    @Override
    public boolean hasIpBlockor(String serviceName, HttpServletRequest request, Integer limit) {
        String ipAddr = IpUtils.getIpAddr(request);

        if (StringUtils.isNoneBlank(ipAddr)) {
            String ipKey = serviceName + "_" + IpUtils.ip2Long(ipAddr);
            String ipTimes = ipBlackListRedis.get(ipKey);
            if (StringUtils.isBlank(ipTimes)) {
                ipTimes = "0";
            }
            long aLong = Long.parseLong(ipTimes);
            return aLong > limit;
        }
        return false;
    }
}
