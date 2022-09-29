import React from 'react'
import ROLE from "../common/RoleStatus";
import {Navigate, Route} from "react-router-dom";
import {useAuth} from "../context/AuthContext";
import AccessDenied from "../pages/AccessDeniedPage";

const PermitRoute = ({
                         children,
                         roles
                     }: {
    children: JSX.Element,
    roles: Array<ROLE>
}) => {
    const {user} = useAuth()

    const hasPermit = user && user.authList.map((value) => {
        roles.map(value => {
            return value.toString() === value
        })
    })
    // if user not login
    if (!user) {
        return <AccessDenied/>
    }
    // user has login ,but hasn't this role
    if (user && !hasPermit) {
        return <AccessDenied/>
    }

    return children;


}

export default PermitRoute;