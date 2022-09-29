package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Gavinin
 * @since 2022-07-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_noticfication")
@ApiModel(value = "SysNoticfication对象", description = "")
public class SysNoticfication implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String uuid;

    private String message;

    private Long receiveUserId;

    private String sendUserName;

    private Long sendUserId;

    private LocalDateTime createData;


}
