import React from 'react'
import styled from "@emotion/styled";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";

const ErrorPage = () => {
    const {t} = useTranslation()

    return(
        <ErrorPageStyle>
            <ErrorContent>
                <h2>{t('error.page-not-found')}</h2>
            </ErrorContent>
            <ErrorContent>
                <p>{t('error.page-not-found-solution')}</p><Link to={"/"}>{t('global.index')}</Link>
            </ErrorContent>
        </ErrorPageStyle>
    )
}

export default ErrorPage;

const ErrorPageStyle = styled.div`
  display: flex;
  max-width: 100%;
  flex-direction: column;
  flex-wrap: nowrap;
  align-items: center;
  
`
const ErrorContent = styled.div`
  display: flex;
`