package com.deuteriun.system.entity;

import com.deuteriun.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author gavin
 */
@Data
public class LoginDTO {

    private String username;

    private String password;

    @JsonFormat(pattern = DateUtils.DATE_TIME_FORMAT, timezone = DateUtils.TIME_ZONE)
    private Date createDate;


}
