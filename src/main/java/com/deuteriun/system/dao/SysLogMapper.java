package com.deuteriun.system.dao;

import com.deuteriun.system.entity.SysLog;

public interface SysLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int insert(SysLog row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int insertSelective(SysLog row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    SysLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int updateByPrimaryKeySelective(SysLog row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_log
     *
     * @mbg.generated Mon Mar 14 01:49:01 CST 2022
     */
    int updateByPrimaryKey(SysLog row);
}