import {Button, Card, Form, Input} from 'antd';
import React from 'react'
import {useAuth} from "../../../context/AuthContext";
import {useTranslation} from "react-i18next";
import styled from "@emotion/styled";

const UserProfile = () => {
    const {user} = useAuth()
    const {t} = useTranslation()

    const onFinish = () => {

    }

    const onFinishFailed = () => {

    }

    return(
        <UserProfileStyle>
            <Form
                name="basic"
                labelCol={{ span: 8 }}
                wrapperCol={{ span: 16 }}
                initialValues={{ remember: true }}
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
            >
                <Form.Item
                    label={t("global.user.name")}
                    name="username"
                    initialValue={user?.name}
                    rules={[{ required: true, message: t("setting.inp-warning")+t("global.user.name") }]}
                >
                    <Input disabled={true}/>
                </Form.Item>

                <Form.Item
                    label={t("global.user.nick-name")}
                    name="nickname"
                    initialValue={user?.nickName}
                    rules={[{ required: true, message: t("setting.inp-warning")+t("global.user.nick-name") }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label={t("global.user.email")}
                    name="email"
                    initialValue={user?.email}
                    rules={[{ required: true, message: t("setting.inp-warning")+t("global.user.email") }]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    label={t("global.user.gender")}
                    name="gender"
                    initialValue={user?.gender}
                    rules={[{ required: true, message: t("setting.inp-warning")+t("global.user.gender") }]}
                >
                    <Input />
                </Form.Item>


                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type="primary" htmlType="submit">
                        {t("setting.user-profile.btn-change")}
                    </Button>
                </Form.Item>
            </Form>
        </UserProfileStyle>
    )
}

export default UserProfile

const UserProfileStyle = styled.div`
  display: flex;
  vertical-align: baseline;
  justify-content: center;
  align-items: center;
  width: 100%;
`