import React from "react";
// import {sendRequestWithData} from "../utils/URLUtils";
// import HTTP_METHODS from "../common/ts/HttpMethod";
//
// const REQUEST_METHOD_URL = "auth"
//
//
// export interface SysRole {
//     id: number,
//     roleCode: string,
//     roleName: string,
//     createUser: number,
//     page: number,
//     limit: number
// }
//
// export interface SysUserRole {
//     id: number,
//     roleId: number,
//     roleCode: string,
//     roleName: string,
//     sysUserId: number,
//     createRoleUserId: number,
//     createDate: string,
// }
//
// export interface SysFilterRole {
//     id: number,
//     sysRoleId: number,
//     sysRole: string,
//     filter: string,
//     createUser: number,
//     createDate: string,
//     modifyDate: string,
//     page: number,
//     limit: number,
// }
//
//
// export const userRoleAdd = (sysUserRole: SysUserRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/ur/add", HTTP_METHODS.POST, sysUserRole)
//
// }
//
// export const userRoleDelete = (sysUserRole: SysUserRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/ur/delete", HTTP_METHODS.DELETE, sysUserRole)
//
// }
//
// export const roleList = (sysRole: SysRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/role/list", HTTP_METHODS.GET, sysRole)
//
// }
//
// export const roleAdd = (sysRole: SysRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/role/add", HTTP_METHODS.POST, sysRole)
//
// }
//
// export const roleDelete = (sysRole: SysRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/role/delete", HTTP_METHODS.DELETE, sysRole)
//
// }
//
// export const filterRoleList = (sysFilterRole: SysFilterRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/ar/list", HTTP_METHODS.GET, sysFilterRole)
//
// }
//
// export const filterRoleAdd = (sysFilterRole: SysFilterRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/ar/add", HTTP_METHODS.POST, sysFilterRole)
//
// }
//
// export const filterRoleDelete = (sysFilterRole: SysFilterRole) => {
//     return sendRequestWithData(REQUEST_METHOD_URL + "/ar/delete", HTTP_METHODS.DELETE, sysFilterRole)
//
// }
