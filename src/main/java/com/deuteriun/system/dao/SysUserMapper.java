package com.deuteriun.system.dao;

import com.deuteriun.system.entity.SysUser;
import com.deuteriun.system.security.entity.UserDo;
import com.deuteriun.system.security.service.SecurityService;


public interface SysUserMapper  {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int insert(SysUser row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int insertSelective(SysUser row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    SysUser selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int updateByPrimaryKeySelective(SysUser row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int updateByPrimaryKey(SysUser row);


    SysUser getUserByName(String userName);
}