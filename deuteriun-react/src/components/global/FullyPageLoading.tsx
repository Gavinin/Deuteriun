import React from "react";
import {Alert, Card, Spin, Typography} from "antd";
import styled from "@emotion/styled";


const FullyPage = styled.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;

`

export const FullyPageLoading = () => <FullyPage>
    <SpinStyle size={"large"}/>
</FullyPage>

export const FullPageError = ({error}: { error: Error | null }) => <FullyPage>
    <CenterCardStyle title="错误">
        <Alert
            showIcon
            description={error?.message ? <>错误原因:{error?.message}</> : <>请检查网络连接</>}
            type="error"
        />
        <Typography.Text type={"danger"}>{error?.message}</Typography.Text>

    </CenterCardStyle>
</FullyPage>

const CenterCardStyle = styled(Card)`
  width: 50%;
`

const SpinStyle = styled(Spin)`
  color: rgba(0,0,0,0.50)
`