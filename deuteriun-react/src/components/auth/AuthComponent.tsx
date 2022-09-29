import React, {useEffect, useState} from "react";
import styled from "@emotion/styled";
import {Button, Card, Divider} from "antd";
import Login from "./login/Login";
import Register from "./register/Register";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../context/AuthContext";

const  AuthComponent = () => {
    const [currentPage, setCurrentPage] = useState(true)
    const {user} = useAuth()
    const {t} = useTranslation()
    const navigate = useNavigate();

    useEffect(()=>{
        if (user!==null){
            navigate("/")
        }
    },[user])

    return (
        <AuthComponentStyle>
            <HeaderStyle/>
            <MainStyle>
                <CardStyle>
                    <H2Title>{currentPage ? t('auth.title-login') : t('auth.title-reg')}</H2Title>
                    {currentPage ? <Login/> : <Register/>}
                    <Divider/>
                    <a onClick={() => {
                        setCurrentPage(!currentPage)
                    }}>
                        {currentPage ? t('auth.switcher-reg') : t('auth.switcher-login')}
                    </a>
                </CardStyle>
            </MainStyle>
        </AuthComponentStyle>
    )
}

export default AuthComponent

export const LongButton = styled(Button)`
  width: 100%;
`

const H2Title = styled.h2`
  margin-bottom: 2.4rem;
  color: rgb(94, 108, 132);
`

const CardStyle = styled(Card)`
  width: 40rem;
  min-height: 56rem;
  padding: 3.2rem;
  border-radius: 0.3rem;
  box-sizing: border-box;
  box-shadow: rgba(49, 50, 52, 0.1) 0 0 10px;
  text-align: center;
`

const HeaderStyle = styled.header`
  width: 100%;
  padding: 5rem 0;
`

const MainStyle = styled.div`
  display: flex;
  display: -webkit-flex;
  justify-content: center;

`

const AuthComponentStyle = styled.div`

`