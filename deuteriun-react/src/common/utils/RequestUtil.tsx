import {isEmpty, isNotEmpty} from "./StringUtils";
import {useAuth} from "../../context/AuthContext";
import {getUserToken, setUserToken} from "../../services/AuthService";
import qs from "qs";
import {useState} from "react";
import useMount from "./hooks/UseMount";

export interface FetchRequest<T = any> {
    "code": number,
    "message": string,
    "date": T,
    "path": string | null,
    "body": any | null
}

export interface IReject {
    reason: string
    token: string
}

export const REJECT_FLAG = "REJECT"


interface RequestConfig extends RequestInit {
    token?: string,
    data?: object;
}

const apiUrl = `${process.env["REACT_APP_API_URL"]}/`
const TOKEN_FLAG = "Token"


const ResultProcessor = async (requestData: FetchRequest, endpoint: string, config: any) => {

    let code: number = requestData.code;
    let codeString: string = code.toString();
    if (isEmpty(codeString)) {
        return Promise.reject("Unknown error!");
    }
    let firstNumber = codeString[0];
    if (firstNumber === "2") {
        return requestData;
    } else if (firstNumber === "3") {
        if (isNotEmpty(requestData.body) && typeof requestData.body.token === "string") {
            let token_tmp = requestData.body.token.toString()
            let reject: IReject = {
                reason: REJECT_FLAG,
                token: token_tmp
            }
            return Promise.reject(reject)
            // setUserToken(token_tmp)
            // config.headers[TOKEN_FLAG] = token_tmp
            // return window.fetch(apiUrl + endpoint, config).then(async response => {
            //     if (response.ok) {
            //         if (code.toString()[0] === "2") {
            //             return response
            //         }
            //         return Promise.reject("内部错误");
            //     }
            //     return Promise.reject("网络错误");
            // })
        }
        return Promise.reject("未知错误");
    } else {
        return Promise.reject(requestData.message);
    }
}

export const http = async (endpoint: string, {data, token, headers, ...customConfig}: RequestConfig) => {
    const config: any = {
        method: 'GET',
        headers: {
            [TOKEN_FLAG]: token ? token : '',
            'Content-Type': data ? 'application/json' : ''
        },
        ...customConfig
    }
    if (config.method.toUpperCase() === "GET") {
        if (data !== null && data !== undefined) {
            endpoint += `?${qs.stringify(data)}`
        }
    } else {
        config.body = JSON.stringify(data || {})
    }
    return window.fetch(apiUrl + endpoint, config)
        .then(async (response) => {
            if (response && response.ok) {
                return ResultProcessor(await response.json(), endpoint, config)
            }
            return Promise.reject("Please check network status!")
        });
};

export const useHttp = () => {
    const {user, setUser} = useAuth()
    return (...[endpoint, config]: Parameters<typeof http>) => {
        return http(endpoint, {
            ...config,
            token: user?.token
        }).then((e) => {
            return e
        }).catch((e) => {
            if ((e as IReject).reason === REJECT_FLAG) {
                // alert(e)
                if (user) {
                    user.token = (e as IReject).token
                    setUser(user)
                    return http(endpoint, {
                        ...config,
                        token: user?.token
                    })
                }

            }
            return Promise.reject(e)
        })

    }
}

