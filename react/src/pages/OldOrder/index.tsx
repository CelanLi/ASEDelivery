import {
  PageContainer,
  ProDescriptionsItemProps,
  ProTable,
} from '@ant-design/pro-components';
import { request } from '@umijs/max';
import { BoxInfo } from '@/types/box';

const OldOrderListApi = 'http://localhost:8089/v1/api/order/find/all';

// 查询list
const handleQueryList = async (query: object) => {
  const res = await request(OldOrderListApi, {
    query,
  });
  return res;
  /* 查找状态为created delivering placed的order*/
};

export default function OldOrderManage() {
  const TAG = 'History Orders';

  const columns: ProDescriptionsItemProps<BoxInfo>[] = [
    {
      title: 'UserAccountSerial',
      dataIndex: 'userAccountSerial',
    },
    /* {
      title: 'status',
      hideInSearch: true,
      dataIndex: 'status', // Generated，Signed，Delivered [0,1,2]
      valueType: 'select',
      valueEnum: {
        0: { text: 'Generated' },
        1: { text: 'Signed' },
        2: { text: 'Delivered' },
      },
    }, */
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
        rowKey="id"
        search={false}
        request={async (params, sorter, filter) => {
          const { data, success } = await handleQueryList({
            ...params,
            sorter,
            filter,
          });
          return {
            data: data.filter((item) => item.status === "picked"),
            success,
          };
        }}
        columns={columns}
      />
    </PageContainer>
  );
}
