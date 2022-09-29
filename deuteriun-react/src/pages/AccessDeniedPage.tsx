import React from 'react'
import styled from "@emotion/styled";
import {Link} from "react-router-dom";
import {useTranslation} from "react-i18next";

const AccessDenied = () => {
    const {t} = useTranslation()

    return(
        <AccessDeniedPageStyle>
            <ErrorContent>
                <h2>{t('error.page-access-denied')}</h2>
            </ErrorContent>
            <ErrorContent>
                <p>{t('error.page-access-denied-solution')}</p><Link to={"/"}>{t('global.index')}</Link>
            </ErrorContent>
        </AccessDeniedPageStyle>
    )
}

export default AccessDenied;

const AccessDeniedPageStyle = styled.div`
  display: flex;
  max-width: 100%;
  flex-direction: column;
  flex-wrap: nowrap;
  align-items: center;
  
`
const ErrorContent = styled.div`
  display: flex;
`