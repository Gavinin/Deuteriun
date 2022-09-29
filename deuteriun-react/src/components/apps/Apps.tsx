import React, {useEffect, useState} from 'react'
import styled from "@emotion/styled";
import {useAuth} from "../../context/AuthContext";
import {Link, Outlet, Route, Routes} from "react-router-dom";
import PrefixStatus from "../../common/PrefixStatus";

interface IApp {
    name: string,
    path: string,
}


const Apps = () => {
    const {user} = useAuth()
    const [appList, setAppList] = useState<IApp[]>([])
    useEffect(() => {
        let testApps: IApp[] = [
            {
                name: "Message",
                path: "/" + PrefixStatus.APP_MSG
            }
        ]
        setAppList(testApps)
    }, [user])

    return (
        <AppBackgroundStyle>
            <AppStyle>
                <AppListStyle>
                    {appList.map((value) => {
                        return <LinkStyle to={PrefixStatus.APPS + value.path} key={Math.random()}>
                            <AppListItemStyle>
                                <AppListItemButtonStyle>{value.name}</AppListItemButtonStyle>
                            </AppListItemStyle>
                        </LinkStyle>
                    })}
                </AppListStyle>
                <AppWindowStyle>
                    <Outlet/>
                </AppWindowStyle>
            </AppStyle>
        </AppBackgroundStyle>
    )
}

export default Apps

const AppStyle = styled.div`
  display: flex;
  margin: 2rem;
  width: 100%;
  background-color: rgb(255, 255, 255);
  border-radius: 0.5rem;

`
const AppBackgroundStyle = styled.div`
  display: flex;
  background-color: rgb(199, 199, 199);
  height: 100%;

`
const AppListStyle = styled.div`
  display: flex;
  width: 15%;
  background-color: rgba(178, 178, 178, 0.5);
  margin-right: 0.5rem;
  flex-direction: column;
  flex-wrap: nowrap;
  justify-content: flex-start;

`
const AppWindowStyle = styled.div`
  display: flex;
  width: 100%;

`
const LinkStyle = styled(Link)`
  width: 100%;
  margin-bottom: 1rem;

`
const AppListItemStyle = styled.div`
  border-radius: 1rem;
  border: 2px solid rgba(59, 59, 59, 0);
  display: flex;
  width: 100%;
  flex-direction: row;
  justify-content: center;
  align-items: center;

  &:hover {
    background-color: rgba(237, 237, 237, 0.5);
    border: 2px solid rgba(59, 59, 59, 0.5);
  }
`

const AppListItemButtonStyle = styled.h2`

`

