import React from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import IndexPage from "../components/index/IndexPage";
import AuthComponent from "../components/auth/AuthComponent";
import PermitRoute from "./PermitRoute";
import ROLE from "../common/RoleStatus";
import {useAuth} from "../context/AuthContext";
import Apps from "../components/apps/Apps";
import MessageDetail from "../components/apps/message/MessageDetail";
import MessageApp from "../components/apps/message/MessageApp";
import VideoFrame from "../components/apps/VideoFrame";
import ErrorPage from "../pages/ErrorPage";
import Settings from "../components/system/Settings";
import PrefixStatus from "../common/PrefixStatus";
import UserProfile from "../components/system/user/UserProfile";
import UserPassword from "../components/system/user/UserPassword";

const RouterConfig = ({children}: { children?: React.ReactNode }) => {
    const {user} = useAuth();
    return (
        <>
            {children}
            <Routes>
                <Route path={PrefixStatus.ROOT} element={<Navigate to={PrefixStatus.HOME}/>}/>
                <Route path={PrefixStatus.HOME} element={<IndexPage/>}/>
                <Route path={PrefixStatus.AUTH} element={<AuthComponent/>}/>
                <Route path={PrefixStatus.APPS} element={<PermitRoute children={<Apps/>} roles={[ROLE.USER]}/>}>
                    <Route path={PrefixStatus.APP_MSG}
                           element={<PermitRoute children={<MessageApp/>} roles={[ROLE.USER]}/>}>
                        <Route path={PrefixStatus.APP_MSG_UUID}
                               element={<PermitRoute children={<MessageDetail/>} roles={[ROLE.USER]}/>}/>
                    </Route>
                    <Route path={PrefixStatus.APP_FVIDEO}
                           element={<PermitRoute children={<VideoFrame url={"www.youtube.com"}/>}
                                                 roles={[ROLE.ADMIN]}/>}
                    />
                </Route>
                <Route path={PrefixStatus.SETTING} element={<PermitRoute children={<Settings/>} roles={[ROLE.USER]}/>}>
                    <Route path={PrefixStatus.SETTING_USER_PREFIX} element={<UserProfile/>}/>
                    <Route path={PrefixStatus.SETTING_USER_PASSWORD} element={<UserPassword/>}/>

                </Route>
                <Route path={PrefixStatus.OTHERS} element={<ErrorPage/>}></Route>
            </Routes>
        </>
    )
}

export default RouterConfig