package com.deuteriun.system.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDo {

    private String name;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;

}
