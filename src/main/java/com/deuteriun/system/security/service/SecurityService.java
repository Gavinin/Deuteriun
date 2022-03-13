package com.deuteriun.system.security.service;

import com.deuteriun.system.security.entity.UserDo;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface SecurityService {

    UserDo getUserDetailByName(String userName);

    List<GrantedAuthority> getGrantedAuthorityListById(Long id);

}
