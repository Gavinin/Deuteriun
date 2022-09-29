import ROLE from "../common/RoleStatus";

export default interface User {
    // id: number,
    name: string,
    nickName: string,
    token: string,
    password: string,
    email?:string,
    gender?:string
    authList: Array<IAuth>
}

export interface IAuth{
    roleCode: string
    roleId: number
    roleName: string
}