import React, { useRef, useState } from 'react';
import {
  ActionType,
  FooterToolbar,
  PageContainer,
  ProDescriptionsItemProps,
  ProTable,
  ProForm,
  ProFormText,
} from '@ant-design/pro-components';
import { Button, Divider, message } from 'antd';
import BaseModal from '@/components/BaseModal';
import { request } from '@umijs/max';
import { BoxInfo } from '@/types/box';

const CustomerAPI = 'http://localhost:8089/v1/api/user/find/all';
const CustomerAddAPI = 'http://localhost:8089/v1/api/user/publish';
const CustomerRemoveAPI = 'http://localhost:8089/v1/api/user/remove';

// 查询list
const handleQueryList = async (query: object) => {
  const res = await request(CustomerAPI, {
    query,
  });
  return res;
  /* 展示角色为customer的列表 */
};

// 新增
const handleAdd = async () => {
  const hide = message.loading('Adding');
  try {
    request(CustomerAddAPI, {
      method: 'post',
      data: {
        /* proForm里写入的数据 */
      },
    })
    hide();
    message.success('Add successfully');
    return true;
  } catch (error) {
    hide();
    message.error('Add failed please try again!');
    return false;
  }
};

// 更新
const handleUpdate = async () => {
  const hide = message.loading('update');
  try {
    request(CustomerAddAPI, {
      method: 'post',
      data: {
        /* 选择数据的serial，这样后端知道更新同一条数据 */
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
const handleRemove = async (selectedRows: []) => {
  const hide = message.loading('deleting');
  if (!selectedRows) return true;
  try {
    request(CustomerRemoveAPI, {
      method: 'post',
      data: {
        /* 选择数据的serial，这样后端知道删除这条数据 */
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

export default function UsersManage() {
  const TAG = 'Customer';
  const [createModalVisible, handleCreateModalVisible] =
    useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] =
    useState<boolean>(false);
  const [updateFormValue, setUpdateFormValue] = useState<BoxInfo>({});
  const actionRef = useRef<ActionType>();
  const [selectedRowsState, setSelectedRows] = useState<[]>([]);
  const columns: ProDescriptionsItemProps<BoxInfo>[] = [
    {
      title: 'ID',
      dataIndex: 'id',
    },
    {
      title: 'name',
      dataIndex: 'name',
    },
    {
      title: 'address',
      dataIndex: 'address',
      valueType: 'text',
    },
    {
      title: 'options',
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
          <a href="">delete</a>
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
        search={{
          labelWidth: 120,
        }}
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
            data: data?.list || [],
            success,
          };
        }}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
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
        </FooterToolbar>
      )}
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
          columns={columns.slice(1)}
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
          }}
          onReset={() => {
            handleUpdateModalVisible(false);
            setUpdateFormValue({});
          }}
        >
          <ProFormText width="md" name="name" label="name" />
          <ProFormText width="md" name="address" label="address" />
        </ProForm>
      </BaseModal>
    </PageContainer>
  );
}
