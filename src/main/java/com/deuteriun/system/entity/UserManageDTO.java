package com.deuteriun.system.entity;

import lombok.Data;
import reactor.util.annotation.NonNull;

import javax.validation.constraints.NotEmpty;


@Data
public class UserManageDTO {

//    @NotEmpty(message = "page is not null")
    private Integer page;
    private Integer limit;
    private String[] users;
    private String username;
    private String user_nickname;
    private String password;

}
