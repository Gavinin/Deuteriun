package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    private Long id;

    /**
     * Role string
     */
    private Integer roleId;

    /**
     * User id from sys_user table
     */
    private Long sysUserId;

    /**
     * The user id that create this role is
     */
    private Long createRoleUserId;

    private LocalDateTime createDate;


}
