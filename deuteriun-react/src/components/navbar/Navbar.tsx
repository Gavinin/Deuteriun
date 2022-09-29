import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import styled from "@emotion/styled";
import {RiFunctionLine, RiHomeLine} from "react-icons/ri";
import NavUserModule from "./NavUser";
import NavMessageModule from "./NavMessage";
import {useAuth} from "../../context/AuthContext";
import NavLanguageSwitcher from "./NavLanguageSwitcher";
import {useTranslation} from "react-i18next";

const Navbar = () => {
    const {user} = useAuth()
    const {t} = useTranslation()

    return (
        <NavBar>
            <NavLogo>
                {t('nav.logo-name')}
            </NavLogo>
            <NavItems>
                <NavItem link={"/"}>
                    <RiHomeLine/>
                </NavItem>
                {user
                    ? <>
                        <NavItem link={"/apps"}>
                            <RiFunctionLine/>
                        </NavItem>
                        <NavLanguageSwitcher/>
                        <NavMessageModule/>
                        <NavUserModule/>
                    </>
                    : <>
                        <NavLanguageSwitcher/>
                        <NavUserModule/>
                    </>
                }
            </NavItems>
        </NavBar>
    );
}

export default Navbar;



export const NavItem = ({link, children}: { link?: string, children: React.ReactNode }) => {
    return (
        <>
            {link
                ? <Link to={link}>
                    <NavItemStyle>
                        {children}
                    </NavItemStyle>
                </Link>
                : <NavItemStyle>
                    {children}
                </NavItemStyle>}
        </>
    )
}


const NavBar = styled.div`
  background-color: rgba(63, 62, 62, 0.97);
  color: black !important;
  height: 6rem;
  border-bottom: 1px solid rgb(0, 0, 0, 30%);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.8rem;
`
export const NavItemStyle = styled.div`
  display: flex;
  align-items: center;
  height: 6rem;
  color: #ffffff;
  padding: 0.5rem 3rem;

  &:hover {
    color: #abc2ca;
    background-color: rgba(255, 255, 255, 0.2);
    border-radius: 0.5rem;
    transition: all 0.2s ease-out;
  }
`

const NavLogo = styled.div`
  display: flex;
  align-items: center;
  height: 6rem;
  color: #ffffff;
  padding: 0.5rem 3rem;
  user-select: none;
  -webkit-user-select: none; /*webkit浏览器*/
`

const NavItems = styled.div`
  height: 6rem;
  display: grid;
  grid-template-columns: repeat(5, auto);
  grid-gap: 10px;
  list-style: none;
  text-align: center;
  width: 70vw;
  justify-content: end;
  margin-right: 2rem;
`

