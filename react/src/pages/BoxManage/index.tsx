import {
  ActionType,
  FooterToolbar,
  PageContainer,
  ProDescriptionsItemProps,
  ProTable,
  ProForm,
  ProFormText,
  ProFormDigit,
} from '@ant-design/pro-components';
import { Button, Divider, message } from 'antd';
import React, { useRef, useState } from 'react';
import BaseModal from '@/components/BaseModal';
import { request, useAccess } from '@umijs/max';
import { BoxInfo } from '@/types/box';
import { useModel } from '@umijs/max';

const BoxListAPI = 'http://localhost:8089/v1/api/box/find/all';
const BoxListAddAPI = 'http://localhost:8089/v1/api/box/publish';
const BoxRemoveAPI = 'http://localhost:8089/v1/api/box/remove';
const BoxUpdateAPI = 'http://localhost:8089/v1/api/box/update/box';
const BoxFindByAccountAPI = 'http://localhost:8089/v1/api/box/findAllByAccount';


const BoxManage: React.FC<unknown> = () => {
  const TAG = 'Box';
  const access = useAccess();
  const [createModalVisible, handleCreateModalVisible] =
    useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] =
    useState<boolean>(false);
  const [updateFormValue, setUpdateFormValue] = useState<BoxInfo>({});
  const actionRef = useRef<ActionType>();

  // 查询list
const handleQueryList = async (query: object) => {
  let res;
  if(access.dispatcher){
    res = await request(BoxListAPI, {
      query,
      });
  }else{
    console.log(currentUser.account)
    res = await request(BoxFindByAccountAPI, {
      method: 'post',
      data: { 
        account: currentUser.account,
      },
      });
  }
  return res;
};

// 新增
const handleAdd = async (value) => {
  const hide = message.loading('Adding');
  try {
    request(BoxListAddAPI, {
      method: 'post',
      data: {
        serial: value.serial,
        name: value.name,
        address: value.address,
      },
    }).then((res: any) => {
      message.success(res.message);
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
  const hide = message.loading('update');
  try {
    request(BoxUpdateAPI, {
      method: 'post',
      data: {
        serial: value.serial,
        name: value.name,
        address: value.address,
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
  const hide = message.loading('deleting');
  try {
    request(BoxRemoveAPI, {
      method: 'post',
      data: {
        serial: record.serial,
        name: record.name,
        address: record.address,
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

  const { initialState, setInitialState } = useModel('@@initialState');
  const { currentUser } = initialState || {};

  const columns: ProDescriptionsItemProps<BoxInfo>[] = [
    {
      title: 'Serial',
      dataIndex: 'serial',
    },
    {
      title: 'Name',
      dataIndex: 'name',
    },
    {
      title: 'Status',
      dataIndex: 'status',
    },
    {
      title: 'Address',
      dataIndex: 'address',
    },
    {
      title: 'options',
      dataIndex: 'option',
      valueType: 'option',
      hideInTable: !access.dispatcher,
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
    <PageContainer>
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
            style={access.dispatcher ? {} : { display: 'none' }}
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
            data: data,
            success,
          };
          /* return {
            data: !access.dispatcher ? data.filter((item) => item.delivererSerial === currentUser.serial) : data,
            success,
          }; */
        }}
        columns={columns}
      />
      {/* 创建 */}
      <BaseModal
        onCancel={() => handleCreateModalVisible(false)}
        modalVisible={createModalVisible}
      >
        <ProTable<BoxInfo, BoxInfo>
          onSubmit={async (value) => {
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
            console.log(
              '%c index~211 %c Submit：',
              'color: #999',
              'color: #ff6700;',
              value,
            );
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
          <ProFormText width="md" name="serial" label="Serial" />
          <ProFormText width="md" name="name" label="Name" />
          <ProFormText width="md" name="status" label="Status" />
          <ProFormText width="md" name="address" label="Address" />
        </ProForm>
      </BaseModal>
    </PageContainer>
  );
};

export default BoxManage;
