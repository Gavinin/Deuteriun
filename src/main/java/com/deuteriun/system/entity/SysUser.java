package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private String password;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    /**
     * User weather be deleted
     */
    private Boolean del;

    /**
     * User has been deleted
     */
    private Boolean ban;


}
