import styled from '@emotion/styled';
import useMount from 'common/utils/hooks/UseMount';
import React, {useState} from 'react'
import {useAuth} from "../../context/AuthContext";
import {Outlet} from "react-router-dom";
import {Link} from 'react-router-dom';
import PrefixStatus from "../../common/PrefixStatus";
import {useTranslation} from "react-i18next";
import ROLE from "../../common/RoleStatus";
import {hasPermit} from "../../common/utils/AuthUtil";

interface LeftMenu {
    path: string,
    name: string,
    default?: boolean
}


const Settings = () => {
    const {user} = useAuth()
    const {t} = useTranslation()
    const [leftMenu, setLeftMenu] = useState<LeftMenu[]>([])

    useMount(()=>{
        let leftMenuInit:LeftMenu[] = [
            {path: PrefixStatus.SETTING_USER_PREFIX, name: t("setting.user-profile.name")},
            {path: PrefixStatus.SETTING_USER_PASSWORD, name: t("setting.user-password.name")},
        ]
        if (hasPermit(ROLE.ADMIN,user?.authList)){
            let  leftMenu_tmp:LeftMenu = {path: PrefixStatus.SETTING_USER_PREFIX, name: t("setting.user-profile.name")}

            leftMenuInit.push(leftMenu_tmp)
        }
        console.log(user?.authList)

        setLeftMenu(leftMenuInit)
    })


    const LeftMenuItemGenerator = ({menus}: { menus: LeftMenu[] }) => {

        return (<>
                {menus.map((value) => {
                    return <LeftMenuItemStyle key={Math.random()}>
                        <Link to={PrefixStatus.SETTING + '/' + value.path}>
                            <MenuContent>
                                {value.name}
                            </MenuContent>
                        </Link>

                    </LeftMenuItemStyle>
                })}
            </>
        )

    }

    useMount(() => {

    })

    return (
        <CenterLayout>
            <SettingsStyle>
                <LeftMenuStyle>
                    <LeftMenuItemGenerator menus={leftMenu}/>
                </LeftMenuStyle>

                <RightWindowStyle>
                    <Outlet/>
                </RightWindowStyle>
            </SettingsStyle>
        </CenterLayout>

    )
}

export default Settings

const CenterLayout = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  height: 100%;
  background-color: rgb(250, 250, 250);
`

const SettingsStyle = styled.div`
  margin-top: 10rem;
  margin-bottom: 10rem;
  background-color: rgb(255, 255, 255);
  border: 0.1rem solid rgb(255, 255, 255);
  border-radius: 1rem;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  flex-grow: 1;
  justify-content: stretch;
  max-width: 935px;
  overflow: hidden;
  width: 100%;

`

const LeftMenuStyle = styled.ul`
  border-right: 1px solid rgb(219, 219, 219);
  display: flex;
  flex-basis: 236px;
  flex-direction: column;
  flex-grow: 0;
  flex-shrink: 0;
  list-style: none;
  padding-inline-start: 0 !important;
`

const RightWindowStyle = styled.div`
  display: flex;
  width: 100%;
`

const LeftMenuItemStyle = styled.li`
  display: list-item;
  text-align: -webkit-match-parent;
  border-left: rgb(255, 255, 255) solid 0.2rem;

  &:hover {
    background-color: rgb(250, 250, 250);
    border-left: rgba(0, 0, 0, 0.6) solid 0.2rem;
  }
`

const MenuContent = styled.div`
  color: black;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  position: relative;
  padding: 1.5rem 1.5rem 1.5rem 2rem;
`