import React from 'react'
import {IAuth} from "../../interfaces/User";

export const hasPermit = (role: string, authList: Array<IAuth>|undefined) => {
    authList?.map((value) => {
        if (value.roleCode === role) {
             return  true;
        }
    })
    return false;
}