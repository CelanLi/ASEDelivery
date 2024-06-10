import {
  ActionType,
  PageContainer,
  ProDescriptionsItemProps,
  ProTable,
  QueryFilter,
} from '@ant-design/pro-components';
import { request } from '@umijs/max';
import { BoxInfo } from '@/types/box';
import { useModel } from '@umijs/max';
import { useRef, useState } from 'react';
import { message } from 'antd';

const NewOrderListFindByAccountApi = 'http://localhost:8089/v1/api/order/findAllByAccount';
const NewOrderListFindBySerialApi = 'http://localhost:8089/v1/api/order/findBySerial';
const NewOrderListFindAllApi = 'http://localhost:8089/v1/api/order/find/all';

export default function OldOrderManage() {
  const TAG = 'New Orders';
  const actionRef = useRef<ActionType>();
  const { initialState, setInitialState } = useModel('@@initialState');
  const { currentUser } = initialState || {};

  async function handleQueryList(query: object) {
    //console.log("query is", query, query.hasOwnProperty("serial"), query.serial !== "")
    console.log(query);
    let res;
    if (query.hasOwnProperty("serial") && query.serial !== "") {
      res = await request(NewOrderListFindBySerialApi, {
        method: 'post',
        data: {
          serial: query.serial,
        },
      });
      console.log('query.serial', query.serial);
      message.success(res.message)
    } else {
      res = await request(NewOrderListFindByAccountApi, {
        method: 'post',
        data: {
          account: currentUser.account,
        },
      });
      console.log(res.message);
      message.success(res.message)
    }
    console.log(res);
    return res;
  }

  const columns: ProDescriptionsItemProps[] = [
    {
      title: 'UserAccountSerial',
      dataIndex: 'userAccountSerial',
      hideInSearch: true,
    },
    {
      title: 'Serial',
      dataIndex: 'serial',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      hideInSearch: true,
    },
    {
      title: 'RealName',
      dataIndex: 'realName',
      hideInSearch: true,
    },
    {
      title: 'DeliverySerial',
      dataIndex: 'deliverySerial',
      hideInSearch: true,
    },
    {
      title: 'DeliveryName',
      dataIndex: 'deliveryName',
      hideInSearch: true,
    },
    {
      title: 'BoxSerial',
      dataIndex: 'boxSerial',
      hideInSearch: true,
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
        rowKey="serial"
        actionRef={actionRef}
        search={{labelWidth: 120}}
        request={async (params,  filter) => {
          console.log(filter,params)
          const { data } = await handleQueryList({
            ...params,
            filter,
          });
          console.log( typeof data)
          if(data.length){
            return{ data: data.filter((item) => item.status !== "picked") } 
          } else {
            const res = [data]
            return {data:res} 
          }
        }}
        columns={columns}
      />
    </PageContainer>
  );
}
