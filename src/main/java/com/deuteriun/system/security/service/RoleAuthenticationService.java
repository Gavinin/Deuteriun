package com.deuteriun.system.security.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface RoleAuthenticationService {

     void adminRoleCheck(List<GrantedAuthority> grantedAuthorityListById) ;

}
