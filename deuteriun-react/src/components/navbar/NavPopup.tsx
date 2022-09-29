import React, {CSSProperties, useEffect, useState} from "react";
import styled from "@emotion/styled";
import {NavItemStyle} from "./Navbar";
import {RiUserLine} from "react-icons/ri";

export const NavPopupItem = ({
                                 logo,
                                 key,
                                 contentStyle,
                                 children
                             }: { logo?: React.ReactNode, key?: string, contentStyle?: CSSProperties, children: React.ReactNode }) => {

    return (
        <NavPopupItemStyle key={key}>
            {logo ? <NavItemLogoStyle>
                {logo}
            </NavItemLogoStyle> : null}
            <NavItemContentStyle style={{userSelect: "none", ...contentStyle}}>
                {children}
            </NavItemContentStyle>
        </NavPopupItemStyle>
    )
}

export const NavPopup = ({
                                   buttonLogo,
                                   children,
                                   banner
                               }: { buttonLogo: React.ReactNode, children: React.ReactNode, banner?: string | null }) => {
    const [isShowMenu, setShowMenu] = useState(false)
    const [nodeContent, setNodeContent] = useState<React.ReactNode | null>(null)
    useEffect(() => {
        if (isShowMenu) {
            setNodeContent(<>
                <FullPageCover onClick={() => {
                    setShowMenu(!isShowMenu)
                }}/>
                <NodeContentStyle onClick={() => {
                    setShowMenu(!isShowMenu)
                }}>
                    <NavPopupMenuStyle>
                        {children}
                    </NavPopupMenuStyle>
                </NodeContentStyle>
            </>)
        } else {
            setNodeContent(null)
        }
    }, [isShowMenu])

    return (
        <NavPopupStyle>
            <NavItemStyle onClick={() => {
                setShowMenu(!isShowMenu)
            }} style={{cursor: 'pointer'}}>
                <ButtonLogo>
                    {buttonLogo}
                </ButtonLogo>
                {banner ? <NotificationBanner>{banner}</NotificationBanner> : null}
            </NavItemStyle>
            {nodeContent}
        </NavPopupStyle>
    )
}
const ButtonLogo = styled.div`
  display: flex;
`

const NotificationBanner = styled.span`
  background-color: red;
  border-radius: 20%;
  display: block;
  //line-height: 14px;
  text-align: center;
  color: #fff;
  font-size: 12px;
  user-select: none;
`

export const NavPopupStyle = styled.section`
  width: 8rem;
  display: flex;
  flex-direction: column;
`


const FullPageCover = styled.div`
  display: flex;
  height: 100%;
  left: 0;
  margin: 0;
  padding: 0;
  position: absolute;
  right: 0;
  top: 0;
  vertical-align: baseline;
  z-index: 0;
`


export const NavPopupMenuStyle = styled.div`
  background-color: rgba(63, 62, 62, 0.97);
  box-shadow: 0 0 10px #141212;
  display: flex;
  border-radius: 1rem;
  text-align: center;
  align-items: stretch;
  flex-direction: column;
  align-content: flex-end;
  justify-content: space-between;
  //margin-left: -16rem;
`
export const NavPopupItemStyle = styled.div`
  color: white;
  display: flex;
  //height: 5rem;
  justify-content: flex-start;
  flex-direction: row;
  align-items: center;
  padding: 1rem;


  &:hover {
    color: #abc2ca;
    background-color: rgba(255, 255, 255, 0.2);
    //border-radius: 1rem;
    padding: 1rem;
    transition: all 0.2s ease-out;
  }
`

const NavItemLogoStyle = styled.div`
  display: flex;
  margin-right: 1rem;
`

const NavItemContentStyle = styled.div`

  display: flex;
  text-align: center;
  font-size: 1.4rem;
  //word-break: keep-all;
  text-overflow: ellipsis;
  width: 100%;
`

const NodeContentStyle = styled.div`
  display: flex;
  position: relative;
  justify-content: center;
`

