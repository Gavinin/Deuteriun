package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author Gavinin
 * @since 2022-03-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user_role")
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;

    /**
     * Role string
     */
//    @JsonIgnore
    private Integer roleId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roleCode;

    /*
        Role Name from sys_user_code
         */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roleName;

    /**
     * User id from sys_user table
     */
    @JsonIgnore
    private Long sysUserId;

    /**
     * The user id that create this role is
     */
    @JsonIgnore
    private Long createRoleUserId;
    @JsonIgnore
    private LocalDateTime createDate;


}
