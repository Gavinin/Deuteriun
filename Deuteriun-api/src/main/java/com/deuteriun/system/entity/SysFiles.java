package com.deuteriun.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @JsonIgnore
    private String filePosition;

    @NotEmpty
    private String fileToken;

    private Long createUserId;

    private LocalDateTime createDate;

    private String moduleName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean del;

    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long page;

    @TableField(exist = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long limit;

}
