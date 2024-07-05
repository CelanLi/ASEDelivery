/* eslint-disable react/jsx-no-duplicate-props */
import React, { useState } from 'react';
import { useModel } from '@umijs/max';
import { Button, Input, Card, message } from 'antd';
import { history } from 'umi';
import style from './index.less';
import md5 from 'md5';
import { request } from '@umijs/max';

const pathMap: { [key: string]: string } = {
  root: '/box-manage',
  dispatcher: '/box-manage',
  customer: '/old-order',
  deliverer: '/courier-box-manage',
};

const LoginAPI = 'http://localhost:8089/v1/api/user/login';
const defaultAvatar = 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png';

export default function () {
  const [account, setAccount] = useState('');
  const [password, setPassword] = useState('');

  const { setInitialState } = useModel('@@initialState');

  async function submit() {

    if (!account || !password) {
      message.error("Please enter the account password")
    }

    let md5Password = md5(password);
    console.log('md5加密后密码：', md5Password);

    request(LoginAPI, {
      method: 'post',
      data: {
        account:account,
        password:md5Password,
      },
    })
      .then((res: any) => {
        console.log('res:', res);
        if(res.message === "login"){
          // store the jwt in local storage
          // localStorage.setItem("token",res.data);

          const { realName, email, account, role  } = res.data;
          setInitialState((s: any) => ({
            ...s,
            currentUser: {
              ...res.data,
              avatar: defaultAvatar,
            },
            name: realName,
            avatar: defaultAvatar,
            role,
            email,
            account,
          }));
          setTimeout(() => {
            history.push(pathMap[role]);
          }, 10);
        } else {
          message.error("Wrong password or account doesn't exist")
        }
      })
      .catch(() => {
        message.error('Failed please try again!');
      });
  }

  return (
    <Card style={{ width: 500, margin: '200px auto' }}>
      <div className="w-full flex justify-center">
        <div className="container lg:px-64 px-8 pt-16">
          <p className={style.title}>Account Login</p>
          <div className="mt-8">
            <p>Account</p>
            <Input
              value={account}
              onChange={(e) => setAccount(e.target.value)}
            />
            <p className="mt-4">Password</p>
            <Input
              type= "password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <div className={style.btnLine}>
              <Button style={{ width: 100 }} onClick={submit}>
                login
              </Button>
            </div>
          </div>
        </div>
      </div>
    </Card>
  );
}
