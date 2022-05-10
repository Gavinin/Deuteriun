package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * System unify user id, search user by this key, user can't change it.
     */
    private String userName;

    /**
     * User modify name , user can change it.
     */
    private String userNickName;

    /**
     * jwt key
     */
    @JsonIgnore
    private String password;

    @JsonIgnore
    private LocalDateTime createDate;

    @JsonIgnore
    private LocalDateTime modifyDate;

    /**
     * User weather be deleted
     */
    @JsonIgnore
    private Boolean del;

    /**
     * User has been deleted
     */
    private Boolean ban;

    @TableField(exist = false)
    private List<SysRole> sysRoleList;


}
