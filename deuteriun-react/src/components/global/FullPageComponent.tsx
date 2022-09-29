import React, {CSSProperties} from "react";
import styled from "@emotion/styled";

const FullPageComponent = ({style, children}: { style?: CSSProperties, children: React.ReactNode }) => {

    return (
        <FullPageComponentStyle style={style}>
            {children}
        </FullPageComponentStyle>
    )

}

export default FullPageComponent;

const FullPageComponentStyle = styled.div`
  background-color: rgba(0, 0, 0, 0.5);
  filter: blur(2px);
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
  flex-direction: row;
  justify-content: center;
  align-items: center;
`
