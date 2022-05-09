package com.deuteriun.system.security.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName RoleAuthenticationAbstractService
 * @Description TODO
 * @Author gavin
 * @Date 10/5/2022 01:02
 * @Version 1.0
 **/
public abstract class RoleAuthenticationAbstractService implements RoleAuthenticationService {

    @Override
    public void adminRoleCheck(List<GrantedAuthority> grantedAuthorityListById) {
        boolean hasRoot = false;
        for (GrantedAuthority grantedAuthority : grantedAuthorityListById) {
            if (grantedAuthority.getAuthority().equals(getRoleFlag())) {
                hasRoot = true;
                break;
            }
        }
        if (hasRoot) {
            try {
                Collection<GrantedAuthority> allAuthorities = getAllGranted();
                if (allAuthorities != null)
                    grantedAuthorityListById.addAll(allAuthorities);
            }catch (Exception ignore){

            }
        }

    }

    protected String getRoleFlag(){
        return null;
    }


    protected Collection<GrantedAuthority> getAllGranted() {
        return null;
    }

}
