import React, {useEffect} from "react";
import User from "../interfaces/User";
import * as AuthServices from "../services/AuthService";
import {
    deleteUserToken,
    getUserToken,
    IAuthContent, setUserToken,
} from "../services/AuthService";
import useMount from "../common/utils/hooks/UseMount";
import {CurrentUserInfoByTokenService} from "../services/UserServices";
import {useAsync} from "../common/utils/hooks/UseAsync";
import {FullyPageLoading} from "../components/global/FullyPageLoading";
import {IReject, REJECT_FLAG} from "../common/utils/RequestUtil";

interface IAuthContext {
    user: User | null,
    login: (authContent: AuthServices.IAuthContent) => Promise<void>
    register: (user: User) => Promise<void>
    logout: () => Promise<void>;
    setUser: (userData: User | null) => void
}

const AuthContext = React.createContext<IAuthContext | undefined>(undefined)
AuthContext.displayName = "AuthContext"

const USER_INFO_DAILY = 60

export const AuthProvider = ({children}: { children: React.ReactNode }) => {
    const {run, isLoading, setData: setUser, data: user} = useAsync<User | null>();
    const login = (authContext: IAuthContent) => AuthServices.Login(authContext).then(setUser);
    const register = (user: User) => AuthServices.Register(user).then(setUser || null);
    const logout = () => AuthServices.Logout(user).then(() => {
        deleteUserToken()
        setUser(null)
    })

    useEffect(() => {
        let interviewer = setInterval(async () => {
            let jwt = user?.token
            if (jwt != null && "" !== jwt) {
                let user_tmp = await CurrentUserInfoByTokenService(jwt)
                if (user_tmp === null || user_tmp.name !== user?.name) {
                    setUser(null)
                } else {
                    user.token = user_tmp.token
                    setUser(user)
                }
            }
        }, USER_INFO_DAILY * 1000)

        return (
            clearInterval(interviewer)
        )
    }, [user])

    useMount(async () => {
        let user: User | null = null;
        let userToken = getUserToken();
        if (userToken != null && "" !== userToken) {
            user = await run(CurrentUserInfoByTokenService(userToken).catch(e => {
                if ((e as IReject).reason === REJECT_FLAG) {
                    userToken = (e as IReject).token.toString()
                    setUserToken(userToken)
                    return CurrentUserInfoByTokenService(userToken)
                }
                return Promise.reject(e)
            }))
            if (user !== null) {
                user.token = userToken
                setUser(user)
            }
        }

    })

    if (isLoading) {
        return <FullyPageLoading/>
    }
    return <AuthContext.Provider children={children} value={{user, setUser, login, register, logout}}/>
}

export const useAuth = () => {
    const context = React.useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth MUST be running in AuthProvider");
    }
    return context;
}