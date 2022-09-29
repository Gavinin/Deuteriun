import React from "react";
import {http, useHttp} from "../common/utils/RequestUtil";
import HttpMethod from "../common/HttpMethod";
import {getFormatDate} from "../common/utils/DateUtils";
import User from "../interfaces/User";
import {CurrentUserInfoByTokenService, RegisterUserService, UserLoginService} from "./UserServices";


const LOCAL_STORAGE_FLAG = "__deuteriun_auth_token__";

export interface IAuthContent {
    username: string,
    password: string,
    createDate: string
}


export const getUserToken = () => {
    return window.localStorage.getItem(LOCAL_STORAGE_FLAG);
}

export const setUserToken = (token: string) => {
    window.localStorage.setItem(LOCAL_STORAGE_FLAG, token || '');
}

export const deleteUserToken = () => {
    window.localStorage.removeItem(LOCAL_STORAGE_FLAG);
}


export const Login = (authContext: IAuthContent) => {
    return UserLoginService(authContext).then(async (data) => {
        let token: string = data?.body.token;
        if (token !== null) {
            setUserToken(token);
            return CurrentUserInfoByTokenService(token)
        }

        return Promise.reject();
    })
}

export const Register = (user: User) => {
    return RegisterUserService(user).then(r => {
        let authContext: IAuthContent = {
            username: user.name,
            password: user.password,
            createDate: getFormatDate()
        }
        return Login(authContext);
    });

}

export const Logout = (user: User | null) => {
    let userToken = getUserToken()
    if (user === null || userToken === null) {
        return Promise.reject("There is no user information")
    }
    return http("apis/logout", {
        token: userToken,
        method: HttpMethod.GET
    }).then().catch((e) => {
        alert(e)
    })
}