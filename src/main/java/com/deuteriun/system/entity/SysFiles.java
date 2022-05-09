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
 * @since 2022-05-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_files")
@ApiModel(value = "SysFiles对象", description = "")
public class SysFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String fileName;

    private String filePosition;

    private Long createUserId;

    private LocalDateTime createDate;

    private LocalDateTime  modifyDate;

    private Boolean delete;


}
