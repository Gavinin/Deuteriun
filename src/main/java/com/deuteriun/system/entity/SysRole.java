package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_role")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * Role string
     */
    private String role;

    /**
     * User id from sys_user table
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long sysUserId;

    /**
     * The user id that create this role is
     */
    @JsonIgnore
    private Long createRoleUserId;

    @JsonIgnore
    private LocalDateTime createDate;


}
