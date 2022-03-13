package com.deuteriun.system.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SysRole {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.id
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.role_code
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    private String roleCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_role.role_name
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    private String roleName;
}