package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author Gavinin
 * @since 2022-05-01
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_login_jwt_blacklist")
@NoArgsConstructor
public class SysLoginJwtBlacklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String userName;

    private String userJwt;

    private Date createDate;

    public SysLoginJwtBlacklist(String userName, String userJwt, Date createDate) {
        this.userName = userName;
        this.userJwt = userJwt;
        this.createDate = createDate;
    }
}
