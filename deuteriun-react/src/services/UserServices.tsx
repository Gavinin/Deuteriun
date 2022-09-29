// import React from "react";
import {http, useHttp} from "../common/utils/RequestUtil";
import HTTP_METHODS from "../common/HttpMethod";
import HttpMethod from "../common/HttpMethod";
import {getFormatDate} from "../common/utils/DateUtils";
import User, {IAuth} from "../interfaces/User";
import {IAuthContent} from "./AuthService";


export const REQUEST_METHOD_URL = "apis/user"

export interface SysUser {
    id:number,
    userName:string,
    userNickName:string,
    ban:boolean,
    sysUserRoleList:Array<IAuth>
    password:string
}

export interface UserManageDTO {
    page: number,
    limit: number,
    users: Array<string>,
    user_id: number,
    username: string,
    user_nickname: string,
    password: string,
    captcha: string,
    token: string
}

export const CurrentUserInfoByTokenService = async (jwt: string) => {
    const handlerUser = (user: SysUser) => {
        let userDo: User = {
            name: user.userName,
            nickName: user.userNickName,
            token: jwt,
            password: "",
            authList: user.sysUserRoleList
        }
        return userDo
    }

    let tokenStr = jwt
    if (tokenStr === null) {
        return Promise.reject("There is no json.")
    }
    return await http(REQUEST_METHOD_URL + "/current-user", {
        token: tokenStr
    }).then(async (data) => {
        return handlerUser(data?.body)
    }).catch((e)=>{
        return Promise.reject(e)
    })
}

export const UserLoginService = (authContext: IAuthContent) => {
    return http(REQUEST_METHOD_URL + "/login", {
        data: {
            "username": authContext.username,
            "password": authContext.password,
            "createDate": authContext.createDate
        },
        headers: {},
        method: HttpMethod.POST
    });
}
export const ListUsersService = (user: UserManageDTO) => {
    const client = useHttp();
    return client(REQUEST_METHOD_URL + "/list", {
        method: HTTP_METHODS.GET,
        data: {
            password: "string",
            userId: 0,
            user_nickname: "string",
            username: "string",
            users: [
                "string"
            ]
        }
    }).then(async (res) => {

    })
}

export const RegisterUserService = async (user: User) => {
    return http(REQUEST_METHOD_URL + "/register", {
        data: {
            "username": user.name,
            "userNickname": user.nickName,
            "password": user.password,
            "createDate": getFormatDate()
        },
        headers: {},
        method: HttpMethod.POST
    }).then((data) => {
        return data;
    })
}

export const UpdateUserService = (user: UserManageDTO) => {
    const client = useHttp();
    client(REQUEST_METHOD_URL + "/update", {
        method: HTTP_METHODS.PUT,
        data: {
            "password": "string",
            "userId": 0,
            "user_nickname": "string",
            "username": "string",
            "users": [
                "string"
            ]
        }
    }).then((data) => {
        return data
    })
}

export const DeleteUserService = async (user: UserManageDTO) => {
    let client = useHttp();
    client(REQUEST_METHOD_URL + "/delete", {
        method: HTTP_METHODS.DELETE,
        data: {}
    }).then((data) => {
        return data
    })

}