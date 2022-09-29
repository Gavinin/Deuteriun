package com.deuteriun.system.service;

import javax.servlet.http.HttpServletRequest;

public interface SecurityPolicyService {
    void ipBlockor(String serviceName, HttpServletRequest request, Integer limit);

    boolean hasIpBlockor(String serviceName, HttpServletRequest request, Integer limit);
}
