package com.deuteriun.system.security.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName PermissionDO
 * @Description TODO
 * @Author gavin
 * @Date 13/5/2022 02:53
 * @Version 1.0
 **/
@Data
public class PermissionDO {
   private List<String> role;
}
