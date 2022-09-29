package com.deuteriun.system.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RefreshToken
 * @Description TODO
 * @Author gavin
 * @Date 18/7/2022 17:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    String token;
}
