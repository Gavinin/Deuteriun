import React, {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {RiLoginCircleLine, RiLogoutBoxRLine, RiSettingsLine, RiUser4Line, RiUserLine} from "react-icons/ri";
import {useAuth} from "../../context/AuthContext";
import {NavItemStyle} from "./Navbar";
import styled from "@emotion/styled";
import {NavPopupItem, NavPopup} from "./NavPopup";
import {useTranslation} from "react-i18next";
import PrefixStatus from "common/PrefixStatus";

const NavUserModule = () => {
    const {user, logout} = useAuth()
    const {t} = useTranslation()
    const navigate = useNavigate();

    return (
        <>
            {user
                ? <NavPopup buttonLogo={<RiUser4Line/>}>
                    <NavPopupItem logo={<RiUserLine/>}>
                        {user.nickName}
                    </NavPopupItem>
                    <Link to={PrefixStatus.SETTING}>
                        <NavPopupItem logo={<RiSettingsLine/>}>
                            {t('nav.settings')}
                        </NavPopupItem>
                    </Link>

                    <UserMenuLogout onClick={() => {
                        logout();
                        navigate("/")
                    }
                    }>
                        <NavPopupItem logo={<RiLogoutBoxRLine/>}>
                            {t('nav.logout')}
                        </NavPopupItem>
                    </UserMenuLogout>

                </NavPopup>
                : <Link to='/auth'>
                    <NavItemStyle>
                        <RiLoginCircleLine/>
                    </NavItemStyle>
                </Link>}
        </>
    )
}
export default NavUserModule;


const UserMenuItemLogo = styled.div`
  display: flex;
  margin-right: 1rem;

`

const UserMenuItemContent = styled.div`
  font-size: 1.4rem;
  display: flex;
`

const UserMenuLogout = styled.div`
  cursor: pointer;
`

