import React, {useEffect, useState} from 'react'
import {changeLanguage} from "i18next";
import {NavPopupItem, NavPopup} from "./NavPopup";
import {IoLanguage} from "react-icons/io5";
import styled from "@emotion/styled";



const NavLanguageSwitcher = () => {
    // const [language,setLanguage] = useState<string>("")

    return (
        <NavPopupItemStyle buttonLogo={<IoLanguage/>}>
            <div onClick={() => {
                changeLanguage('cn')
            }}>
                <NavPopupItem>
                    简
                </NavPopupItem>
            </div>
            <div onClick={() => {
                changeLanguage('hk')
            }}>
                <NavPopupItem>
                    繁
                </NavPopupItem>
            </div>
            <div onClick={() => {
                changeLanguage('en-us')
            }}>
                <NavPopupItem>
                    EN
                </NavPopupItem>
            </div>
        </NavPopupItemStyle>
    )
}

export default NavLanguageSwitcher

const NavPopupItemStyle = styled(NavPopup)`
  width: 3rem;
`

