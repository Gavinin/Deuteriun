import {useAuth} from "../../../context/AuthContext";
import {IAuthContent} from "../../../services/AuthService";
import {getFormatDate} from "../../../common/utils/DateUtils";
import React, {Fragment, useState} from "react";
import {Button, Checkbox, Form, Input} from "antd";
import {LongButton} from "../AuthComponent";
import {useTranslation} from "react-i18next";
import FullPageComponent from "../../global/FullPageComponent";

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

const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};

const Register = () => {
    const [agreement, setAgreement] = useState(false)
    let {user, login} = useAuth();
    const submitHandle = async (values: { username: string, password: string, captcha: string }) => {
        let loginInfo: IAuthContent = {
            "username": values.username,
            "password": values.password,
            "createDate": getFormatDate()
        }
        await login(loginInfo)
    }
    const {t} = useTranslation()

    return (
        <Fragment>
            <Form
                {...formItemLayout}
                onFinish={submitHandle}
            >
                <Form.Item
                    name={'username'}
                    rules={[{required: true, message: t('auth.register.user-name-w')}]}
                    label={t('auth.register.user-name-l')}
                >
                    <Input type={'text'} id={'username'} placeholder={t('auth.register.user-name-p')}/>
                </Form.Item>
                <Form.Item
                    name={'user-nickname'}
                    rules={[{required: true, message: t('auth.register.nick-name-w')}]}
                    label={t('auth.register.nick-name-l')}
                >
                    <Input type={'text'} id={'user-nickname'} placeholder={t('auth.register.nick-name-p')}/>
                </Form.Item>
                <Form.Item
                    name="password"
                    label={t('auth.register.psw-l')}
                    rules={[
                        {
                            required: true,
                            message: t('auth.register.psw-w'),
                        },
                    ]}
                    hasFeedback
                >
                    <Input.Password/>
                </Form.Item>

                <Form.Item
                    name="confirm"
                    label={t('auth.register.psw-check-l')}
                    dependencies={['password']}
                    hasFeedback
                    rules={[
                        {
                            required: true,
                            message: t('auth.register.psw-check-w'),
                        },
                        ({getFieldValue}) => ({
                            validator(_, value) {
                                if (!value || getFieldValue('password') === value) {
                                    return Promise.resolve();
                                }
                                return Promise.reject(new Error(t('auth.register.psw-check-e')));
                            },
                        }),
                    ]}
                >
                    <Input.Password/>
                </Form.Item>
                <Form.Item
                    name="agreement"
                    valuePropName="checked"
                    rules={[
                        {
                            validator: (_, value) =>
                                value ? Promise.resolve() : Promise.reject(t('auth.register.need-agreement')),
                        },
                    ]}
                    {...tailFormItemLayout}
                >
                    <Checkbox>
                        {t('auth.register.have-agreement')} <a onClick={() => {
                        setAgreement(true)
                    }} href="components/auth/register/Register#">{t('auth.register.agreement')}</a>
                    </Checkbox>
                </Form.Item>

                {/*<Form.Item name={'captcha'} rules={[{required: true, message: "请输入验证码"}]}>*/}
                {/*    <Input type={'text'} id={'captcha'}/>*/}
                {/*</Form.Item>*/}
                <LongButton htmlType={'submit'} type={'primary'}>{t('auth.register.name')}</LongButton>
            </Form>
            {agreement
                ?
                <div onClick={() => {
                    setAgreement(false)
                }
                }>
                    <FullPageComponent>
                        <div>dsadasdas</div>
                    </FullPageComponent>
                </div>

                : null}
        </Fragment>

    )

}

export default Register