import React, {useEffect, useState} from "react";
import {RiBug2Line, RiChat1Line, RiUserLine} from "react-icons/ri";
import {NavPopup, NavPopupItem} from "./NavPopup";
import {useHttp} from "../../common/utils/RequestUtil";
import styled from "@emotion/styled";
import {Link} from "react-router-dom";
import {useTranslation} from 'react-i18next';


interface RecentNotification {
    uuid: string
    message: string
    sendUserName: string
    createData?: number
}

interface UserNotification {
    notificationNumber: number
    recentNotifications: RecentNotification[]
}

const MAX_MESSAGE_NUM = 5;
const MSG_REFRESH_DALY = 60;

const NavMessageModule = () => {
    const client = useHttp()
    const [notificationFlag, setNotificationFlag] = useState<string | null>(null)
    const [msgList, setMsgList] = useState<RecentNotification[] | null>()
    const [error, setError] = useState<string | null>(null)
    const {t} = useTranslation();

    useEffect(() => {
        const interval = setInterval(() => {
            client("apis/notification/getnotification", {}).then(r => {
                    let rn: RecentNotification[] = r.body
                    let data: UserNotification = {
                        notificationNumber: rn.length,
                        recentNotifications: rn
                    }
                    if (data.notificationNumber > 0) {
                        setNotificationFlag(data.notificationNumber.toString())
                        setMsgList(rn)
                    }
                }
            ).catch((e) => {
                setNotificationFlag("!")
                setError(e)
            })
        }, MSG_REFRESH_DALY * 1000);
        return () => clearInterval(interval);
    }, [notificationFlag])

    return (
        <NavPopup buttonLogo={<RiChat1Line/>} banner={notificationFlag} key={Math.random()}>
            {error != null ? <NavPopupItem logo={<RiBug2Line/>}>
                {error}
            </NavPopupItem> : (msgList == null
                ? <NavPopupItem contentStyle={{minWidth: "20rem"}} logo={<RiUserLine/>}>
                    {t('msg.notifier.no-message')}
                </NavPopupItem>
                : msgList.map((item, index) => {
                    if (index <= MAX_MESSAGE_NUM) {
                        return <Link to={"/apps/msg/" + item.uuid} key={Math.random()}>
                            <MsgPopupItem  key={Math.random()}>
                                <NavPopupItem contentStyle={{minWidth: "20rem"}} logo={<RiUserLine/>}>
                                    {item.sendUserName} {t('msg.notifier.send')} {item.message}
                                </NavPopupItem>
                            </MsgPopupItem>
                        </Link>

                    } else {
                        return null
                    }
                }))}
        </NavPopup>
    )
}

export default NavMessageModule

const MsgPopupItem = styled.div`
  //height: 3rem;
  //border-bottom: rgba
`