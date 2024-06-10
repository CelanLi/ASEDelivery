import React, { useRef, useState } from 'react';
import {
  ActionType,
  FooterToolbar,
  PageContainer,
  ProDescriptionsItemProps,
  ProTable,
  ProForm,
  ProFormText,
  ProFormSelect,
} from '@ant-design/pro-components';
import { Button, Divider, message } from 'antd';
import BaseModal from '@/components/BaseModal';
import { request } from '@umijs/max';
import { BoxInfo } from '@/types/box';
import md5 from 'md5';
import Password from 'antd/es/input/Password';

const AccountAPI = 'http://localhost:8089/v1/api/user/find/all';
const AccountAddAPI = 'http://localhost:8089/v1/api/user/publish';
const AccountRemoveAPI = 'http://localhost:8089/v1/api/user/remove';
const AccountUpdateAPI ='http://localhost:8089/v1/api/user/update';
const EmailAPI='http://localhost:8089/v1/api/user/email'

const roleMap = {
  dispatcher: 'dispatcher',
  deliverer: 'deliverer',
  customer: 'customer',
};

// 查询list
const handleQueryList = async (query: object) => {
  const res = await request(AccountAPI, {
    query,
  });
  return res;
};

// 新增
const handleAdd = async (value) => {
  const hide = message.loading('Adding');
  const Md5Password=md5(value.password);
  try {
    request(AccountAddAPI, {
      method: 'post',
      data: {
        account: value.account,
        serial: value.serial,
        password: Md5Password,
        role: value.role,
        realName: value.realName,
        rfid:value.rfid,
        email:value.email
      },
    })
    
    .then((res: any) => {
          message.info(res.message);
        })
    request(EmailAPI, {
      method: 'post',
      data: {
        account: value.account,
        serial: value.serial,
        password: value.password,
        role: value.role,
        realName: value.realName,
        rfid:value.rfid,
        email:value.email
      },
    })
    return true;
  } catch (error) {
    hide();
    message.error('Add failed please try again!');
    return false;
  }
};

// 更新
const handleUpdate = async (value) => {
  /* const deleteOldData = (data) => {
    let serial = data.serial;
    return serial;
  } */
  const hide = message.loading('update');
  try {
    const Md5Password=md5(value.password);
    console.log(value.serial)
    request(AccountUpdateAPI, {
      method: 'post',
      data: {
        account: value.account,
        serial: value.serial,
        password: Md5Password,
        role: value.role,
        realName: value.realName,
        rfid:value.rfid,
        email:value.email
      },
    })
    hide();
    message.success('Update successfully');
    return true;
  } catch (error) {
    hide();
    message.error('Failed to update configuration please try again !！');
    return false;
  }
};

// delete
const handleRemove = async (record) => {
  /* console.log(selectedRows);
  if (!selectedRows) return true; */
  const hide = message.loading('deleting');
  try {
    request(AccountRemoveAPI, {
      method: 'post',
      data: {
        account: record.account,
        serial: record.serial,
        password: record.password,
        role: record.role,
        realName: record.realName,
        rfid:record.rfid,
        email:record.email
      },
    })
    hide();
    message.success('Deleted successfully');
    return true;
  } catch (error) {
    hide();
    message.error('delete failed, please try again');
    return false;
  }
};

export default function AccountManage() {
  const TAG = 'Account';
  const [createModalVisible, handleCreateModalVisible] =
    useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] =
    useState<boolean>(false);
  const [updateFormValue, setUpdateFormValue] = useState<BoxInfo>({});
  const actionRef = useRef<ActionType>();
  const [selectedRowsState, setSelectedRows] = useState<[]>([]);
  const columns: ProDescriptionsItemProps<BoxInfo>[] = [
    {
      title: 'Account',
      dataIndex: 'account',
    },
    {
            title: 'Serial',
            dataIndex: 'serial',
          },
    {
      title: 'Password',
      dataIndex: 'password',
      hideInSearch: true,
    },
    {
      title: 'Role',
      dataIndex: 'role', // dispatcher deliverer customer
      valueEnum: roleMap,
    },
    {
      title: 'RealName',
      dataIndex: 'realName',
    },
    {
      title: 'RFID',
      dataIndex: 'rfid',
    },
    {
      title: 'Email',
      dataIndex: 'email', 
    },
    {
      title: 'Options',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <>
          <a
            onClick={() => {
              handleUpdateModalVisible(true);
              setUpdateFormValue(record);
            }}
          >
            update
          </a>
          <Divider type="vertical" />
          <a onClick={() => {
              handleRemove(record);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }}>delete</a>
        </>
      ),
    },
  ];

  return (
    <PageContainer
      header={{
        title: `${TAG} Management`,
      }}
    >
      <ProTable<BoxInfo>
        headerTitle={`${TAG} List`}
        actionRef={actionRef}
        rowKey="id"
        search={false}
        toolBarRender={() => [
          <Button
            key="1"
            type="primary"
            onClick={() => handleCreateModalVisible(true)}
          >
            Create
          </Button>,
        ]}
        request={async (params, sorter, filter) => {
          const { data, success } = await handleQueryList({
            ...params,
            sorter,
            filter,
          });
          return {
            data: data || [],
            success,
          };
        }}
        columns={columns}
        /* rowSelection={{
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }} */
      />
      {selectedRowsState?.length > 0 && (
        {/* <FooterToolbar
          extra={
            <div>
              已选择{' '}
              <a style={{ fontWeight: 600 }}>{selectedRowsState.length}</a>{' '}
              项&nbsp;&nbsp;
            </div>
          }
        >
          <Button
            onClick={async () => {
              await handleRemove(selectedRowsState);
              setSelectedRows([]);
              actionRef.current?.reloadAndRest?.();
            }}
          >
            批量delete
          </Button>
        </FooterToolbar> */}
      )}
      {/* 创建 */}
      <BaseModal
        onCancel={() => handleCreateModalVisible(false)}
        modalVisible={createModalVisible}
      >
        <ProTable<BoxInfo, BoxInfo>
          onSubmit={async (value) => {
            console.log(value);
            const success = await handleAdd(value);
            if (success) {
              handleCreateModalVisible(false);
              if (actionRef.current) {
                actionRef.current.reload();
              }
            }
          }}
          rowKey="id"
          type="form"
          columns={columns}
        />
      </BaseModal>
      {/* 更新title */}
      <BaseModal
        onCancel={() => {
          handleUpdateModalVisible(false);
          setUpdateFormValue({});
        }}
        modalVisible={updateModalVisible}
      >
        <ProForm
          submitter={{
            searchConfig: {
              resetText: 'Cancel',
              submitText: 'Submit',
            },
          }}
          initialValues={updateFormValue}
          onFinish={async (value) => {
            console.log(value);
            handleUpdate(value);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }}
          onReset={() => {
            handleUpdateModalVisible(false);
            setUpdateFormValue({});
          }}
        >
          <ProFormText width="md" name="account" label="Account" />
          <ProFormText width="md" name="serial" label="Serial" />
          
          <ProFormText width="md" name="password" label="Password" />
          <ProFormSelect
            valueEnum={roleMap}
            width="md"
            name="role"
            label="type"
          />
          <ProFormText width="md" name="realName" label="RealName" />
          <ProFormText width="md" name="rfid" label="RFID" />
          <ProFormText width="md" name="email" label="Email" />
        </ProForm>
      </BaseModal>
    </PageContainer>
  );
}
