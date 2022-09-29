import {Button, Card, Form, Input} from 'antd';
import React from 'react'
import {useAuth} from "../../../context/AuthContext";
import {useTranslation} from "react-i18next";
import styled from "@emotion/styled";

const UserPassword = () => {
    const {user} = useAuth()
    const {t} = useTranslation()

    const onFinish = () => {

    }

    const onFinishFailed = () => {

    }

    return(
        <UserPasswordStyle>
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
                    name="old_password"
                    label={t("setting.user-password.o-pass")}
                    rules={[
                        {
                            required: true,
                            message: t("setting.inp-warning")+t("setting.user-password.o-pass"),
                        },
                    ]}
                    hasFeedback
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    name="password"
                    label={t("setting.user-password.n-pass")}
                    rules={[
                        {
                            required: true,
                            message: t("setting.inp-warning")+t("setting.user-password.n-pass"),
                        },
                    ]}
                    hasFeedback
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    name="confirm"
                    label={t("setting.user-password.n-pass-c")}
                    dependencies={['password']}
                    hasFeedback
                    rules={[
                        {
                            required: true,
                            message: t("setting.inp-warning")+t("setting.user-password.o-pass-c"),
                        },
                        ({ getFieldValue }) => ({
                            validator(_, value) {
                                if (!value || getFieldValue('password') === value) {
                                    return Promise.resolve();
                                }
                                return Promise.reject(new Error(t("setting.user-password.error")));
                            },
                        }),
                    ]}
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
                    <Button type="primary" htmlType="submit">
                        {t("setting.user-profile.btn-change")}
                    </Button>
                </Form.Item>
            </Form>
        </UserPasswordStyle>
    )
}

export default UserPassword

const UserPasswordStyle = styled.div`
  display: flex;
  vertical-align: baseline;
  justify-content: center;
  align-items: center;
  width: 100%;
`