package com.deuteriun.system.service;

import com.deuteriun.system.security.entity.SecurityUser;

/**
 * Security service provide methods about spring security function
 * @author Gavin
 */
public interface SecurityService {

    String generateJWTBySysUserName(String username);

    /**
     * UserDetail is spring security authority component
     * this method is for creat UserDetail object
     * @param userName form database
     * @return UserDetail
     */
    SecurityUser getSecurityUserByName(String userName);

    /**
     * Get User from current security context holder
     * @return SecurityUser
     */
    SecurityUser getCurrentUser();

    /**
     * this method let token  effectiveness immediately
     * @param token from HttpServletRequest
     * @return is it fish
     */
    Boolean logoutNow(String token);


}
