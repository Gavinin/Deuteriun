import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import 'antd/dist/antd.css';
import {
    Button,
    DatePicker,
    Input, Select,
} from 'antd';
import {useAsync} from "../../../common/utils/hooks/UseAsync";
import useMount from "../../../common/utils/hooks/UseMount";
import {useHttp} from "../../../common/utils/RequestUtil";
import styled from "@emotion/styled";

interface IReceiverMsg {
    sender: string
    newest_msg: string
    data: string
}

interface IReceiverMsgFilter {
    sender?: string[]
    start_data?: string
    end_data?: string
}

const MessageItem = ({rm}: { rm: IReceiverMsg }) => {
    return <MessageItemStyle>
        <MessageItemSenderStyle className={"msg-user"}>{rm.sender}</MessageItemSenderStyle>
        <MessageItemContentStyle className={"msg-content"}>{rm.newest_msg}</MessageItemContentStyle>
        <MessageItemDataStyle className={"msg-data"}>{rm.data}</MessageItemDataStyle>
    </MessageItemStyle>
}

const MessageApp = () => {
    const {Option} = Select;
    const client = useHttp()
    const {run} = useAsync()
    const [rmf, setRmf] = useState<IReceiverMsgFilter>({})
    const [rmfTmp, setRmfTmp] = useState<IReceiverMsgFilter>({})
    const [rmfUserList, setRmfUserList] = useState<string[]>([])
    const [rmList, setRmList] = useState<IReceiverMsg[]>([])

    const MessageFilter = () => {

        return <MessageFilterStyle>
            <Input.Group compact>
                <Select
                    mode="multiple"
                    allowClear
                    style={{width: '100%'}}
                    placeholder="Please select"
                    onChange={(value: string[]) => {
                        rmfTmp.sender = value
                        setRmfTmp(rmfTmp)
                    }}
                >
                    {rmfUserList.map((value) => {
                        return <Option key={value}>{value}</Option>
                    })}
                </Select>
                <DatePicker.RangePicker
                    allowEmpty={[true, true]}
                    allowClear={true}
                    style={{
                        width: '60%',
                    }}
                    onChange={(data, dateStrings) => {
                        rmfTmp.start_data = dateStrings[0]
                        rmfTmp.end_data = dateStrings[1]
                        setRmfTmp(rmfTmp)
                    }
                    }
                />
                <Button onClick={() => {
                    setRmf(rmfTmp)
                }
                } type="primary">Search</Button>
            </Input.Group>
        </MessageFilterStyle>
    }

    const getRmList = async () => {
        return await client("apis/msg/listAll", {}).then((data) => {
            setRmList(data.body)
            let rlTmp: Array<string> = []
            rmList.map((value) => {
                if (!(rlTmp.includes(value.sender))) {
                    rlTmp.push(value.sender)
                }
            })
            setRmfUserList(rlTmp)
        })
    }
    useMount(() => {
        run(getRmList())
    })

    useEffect(() => {
        getRmList()
    }, [])

    return (
        <MessageAppStyle>
            <LeftMessageSide>
                <MessageFilter/>
                {/*<MessageItems>*/}
                {/*    {rmList.map((value, index, array) => {*/}
                {/*        return <MessageItem rm={value}/>*/}
                {/*    })*/}
                {/*    }*/}
                {/*</MessageItems>*/}
            </LeftMessageSide>
            <RightMessageSide>
                <h1>23232</h1>
            </RightMessageSide>

        </MessageAppStyle>
    )
}

export default MessageApp;

const MessageAppStyle = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-direction: row;
  width: 100%;
`

const MessageItems = styled.div`

`
const MessageFilterStyle = styled.div`
  display: flex;
  //width: 15%;
`

const MessageItemStyle = styled.div`
  display: grid;
  grid-template-areas:
                "msg-user msg-content msg-content"
                "msg-data msg-data msg-data";
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
`

const MessageItemSenderStyle = styled.div`

`
const MessageItemContentStyle = styled.div`

`
const MessageItemDataStyle = styled.div`

`
const LeftMessageSide = styled.div`
  display: flex;
  flex-direction: row;
  width: 30%;
  margin-right: 1rem;
`
const RightMessageSide = styled.div`
  display: flex;
  width: 100%;
`
