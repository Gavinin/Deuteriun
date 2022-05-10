package com.deuteriun.system.entity;

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
    private Long user_id;
    private String username;
    private String user_nickname;
    private String password;
}
