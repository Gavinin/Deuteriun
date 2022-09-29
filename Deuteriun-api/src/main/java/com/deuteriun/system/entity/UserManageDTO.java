package com.deuteriun.system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @ClassName UserManageDTO
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 01:01
 * @Version 1.0
 **/
@Data
public class UserManageDTO {

    private Integer page;

    private Integer limit;

    private String[] users;

    private Long userId;

    private String username;

    @JsonProperty("user_nickname")
    private String userNickname;

    private String password;
}
