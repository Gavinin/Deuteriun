import React from "react"
import styled from "@emotion/styled";


const WindowProvider = ({children}: { children: React.ReactNode }) => {
    return (
        <WindowStyle>
            {children}
        </WindowStyle>
    )
}

export default WindowProvider;

const WindowStyle = styled.main`
  height: calc(100vh - 6rem);
`