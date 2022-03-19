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
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Log id

     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user id from sys_user table
     */
    private Long sysUserId;

    /**
     * Event detail
     */
    private String event;

    private LocalDateTime eventId;

    /**
     * 1:Info
2:Warning
3:Error
     */
    private Boolean level;

    private Boolean ip;


}
