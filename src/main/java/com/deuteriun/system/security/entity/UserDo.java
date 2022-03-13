package com.deuteriun.system.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class UserDo {

    private String name;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;

}
