import React, {Component, Fragment} from "react";
import {useAuth} from "../../../context/AuthContext";
import {IAuthContent} from "../../../services/AuthService";
import {Button, Form, Input} from "antd";
import {getFormatDate} from "../../../common/utils/DateUtils";
import {LongButton} from "../AuthComponent";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";

const formItemLayout = {
    labelCol: {
        xs: {span: 24},
        sm: {span: 8},
    },
    wrapperCol: {
        xs: {span: 24},
        sm: {span: 16},
    },
};

const Login = () => {
    const {login,user} = useAuth();
    const {t} = useTranslation()

    const submitHandle = async (values: { username: string, password: string, captcha: string }) => {
        let loginInfo: IAuthContent = {
            "username": values.username,
            "password": values.password,
            "createDate": getFormatDate()
        }
        await login(loginInfo)
    }

    return (
        <Fragment>
            <div>
                <Form
                    {...formItemLayout}
                    onFinish={submitHandle}
                >
                    <Form.Item
                        name={'username'}
                        rules={[{required: true, message: t('auth.login.user-name-w')}]}
                        label={t('auth.login.user-name-l')}
                    >
                        <Input type={'text'} id={'username'} />
                    </Form.Item>
                    <Form.Item
                        name={'password'}
                        rules={[{required: true, message: t('auth.login.psw-w')}]}
                        label={t('auth.login.psw-l')}
                    >
                        <Input type={'password'} id={'password'} />
                    </Form.Item>
                    {/*<Form.Item*/}
                    {/*    name={'captcha'}*/}
                    {/*    rules={[{required: true, message: "请输入验证码"}]}*/}
                    {/*    label="验证码"*/}
                    {/*>*/}
                    {/*    <Input type={'text'} id={'captcha'}/>*/}
                    {/*</Form.Item>*/}
                    <LongButton htmlType={'submit'} type={'primary'}>{t('auth.login.name')}</LongButton>
                </Form>
            </div>
        </Fragment>

    )

}

export default Login