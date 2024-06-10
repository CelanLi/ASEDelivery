import React, { useRef, useState, useEffect } from 'react';
import {
  ActionType,
  FooterToolbar,
  PageContainer,
  ProDescriptionsItemProps,
  ProTable,
  ProForm,
  ProFormText,
} from '@ant-design/pro-components';
import { Button, Divider, message, Modal, QRCode } from 'antd';
import BaseModal from '@/components/BaseModal';
import { request, useAccess } from '@umijs/max';
import { BoxInfo } from '@/types/box';
import { useModel } from '@umijs/max';
import { QrReader } from 'react-qr-reader';
import QRcode from 'qrcode.react';
import UploadQRCode from '@/components/DragAndDrop';

const AccountAPI = 'http://localhost:8089/v1/api/user/find/all';
const OrderAPI = 'http://localhost:8089/v1/api/order/find/all';
const OrderListFindByAccountApi =
  'http://localhost:8089/v1/api/order/findAllByAccount';
const OrderAddAPI = 'http://localhost:8089/v1/api/order/publish';
const OrderUpdateAPI = 'http://localhost:8089/v1/api/order/update/delivery';
const OrderRemoveAPI = 'http://localhost:8089/v1/api/order/remove';
const OrderUpdateCollectionAPI =
  'http://localhost:8089/v1/api/order/update/status_collection';
const EmailAPI = 'http://localhost:8089/v1/api/user/email';

/* const handleQueryUserList = async () => {
  const res = await request(AccountAPI);
  return res;
}; */

// 验证快递员身份
const delivererVerification = async (value) => {
  try {
    request(OrderUpdateCollectionAPI, {
      method: 'post',
      data: {
        account: value.account,
        serial: value.serial,
        userAccountSerial: value.userAccountSerial,
        deliverySerial: value.deliverySerial,
      },
    }).then((res: any) => {
      message.info(res.message);
    });

    return true;
  } catch (error) {
    message.error('Failed,please try again !！');
    return false;
  }
};
//下载qrcode
const changeCanvasToPic = async (serial) => {
  const canvasImg = document.getElementById(serial); // 获取canvas类型的二维码
  const img = new Image();
  img.src = canvasImg.toDataURL('image/png'); // 将canvas对象转换为图片的data url
  const downLink = document.getElementById('down_link' + serial);
  downLink.href = img.src;
  downLink.download = 'qrcode' + serial; // 点击下载图片name
};

// 新增
const handleAdd = async (value) => {
  const hide = message.loading('Adding');
  try {
    request(OrderAddAPI, {
      method: 'post',
      data: {
        serial: value.serial,
        userAccountSerial: value.userAccountSerial,
        realName: value.realName,
        deliverySerial: value.deliverySerial,
        deliveryName: value.deliveryName,
        boxSerial: value.boxSerial,
      },
    }).then((res: any) => {
      message.info(res.message);
    });

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
    request(OrderUpdateAPI, {
      method: 'post',
      data: {
        serial: value.serial,
        userAccountSerial: value.userAccountSerial,
        realName: value.realName,
        deliverySerial: value.deliverySerial,
        deliveryName: value.deliveryName,
        boxSerial: value.boxSerial,
      },
    });
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
    request(OrderRemoveAPI, {
      method: 'post',
      data: {
        serial: record.serial,
        userAccountSerial: record.userAccountSerial,
        realName: record.realName,
        deliverySerial: record.deliverySerial,
        deliveryName: record.deliveryName,
        boxSerial: record.boxSerial,
      },
    });
    hide();
    message.success('Deleted successfully');
    return true;
  } catch (error) {
    hide();
    message.error('delete failed, please try again');
    return false;
  }
};

export default function OrderManage() {
  const [startScan, setStartScan] = useState(false);
  const [data, setData] = useState('');
  const [dataJson, setDataJson] = useState('23');
  const TAG = 'Orders';
  const access = useAccess();
  const [createModalVisible, handleCreateModalVisible] =
    useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] =
    useState<boolean>(false);
  const [updateFormValue, setUpdateFormValue] = useState<BoxInfo>({});
  const actionRef = useRef<ActionType>();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [qrData, setQrData] = useState({});

  const { initialState, setInitialState } = useModel('@@initialState');
  const { currentUser } = initialState || {};

  const handleScan = (scanData) => {
    // if (scanData && scanData !== "") {
    // setData(scanData);
    setDataJson(eval('(' + scanData + ')'));
    // const orderForVeri={
    //   account:currentUser,
    //   serial:dataJson["serial"],
    //   deliverySerial:dataJson["deliverySerial"]
    // }
    // delivererVerification(orderForVeri)
    // }
  };
  // const handleError = (err) => {
  //   console.error(err);
  // };

  const handleQueryList = async (query: object) => {
    let res;
    if (access.dispatcher) {
      res = await request(OrderAPI, {
        query,
      });
    } else {
      res = await request(OrderListFindByAccountApi, {
        method: 'post',
        data: {
          account: currentUser.account,
        },
      });
    }
    return res;
  };

  const columns: ProDescriptionsItemProps<BoxInfo>[] = [
    {
      title: 'Serial',
      dataIndex: 'serial',
    },
    {
      title: 'UserAccountSerial',
      dataIndex: 'userAccountSerial',
    },
    /* {
      title: 'status',
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
    },
    {
      title: 'DeliverySerial',
      dataIndex: 'deliverySerial',
    },
    {
      title: 'DeliveryName',
      dataIndex: 'deliveryName',
    },
    {
      title: 'BoxSerial',
      dataIndex: 'boxSerial',
    },
    {
      title: 'options',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <>
          {access.dispatcher ? (
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
              <a
                onClick={() => {
                  handleRemove(record);
                  if (actionRef.current) {
                    actionRef.current.reload();
                  }
                }}
              >
                delete
              </a>
              <Divider type="vertical" />
            </>
          ) : null}
          <a
            id={'down_link' + record.serial}
            onClick={() => {
              console.log(record.serial);
              changeCanvasToPic(record.serial);
            }}
          >
            QR code
            <QRcode
              id={record.serial}
              value={
                "{'userAccountSerial':'" +
                record.userAccountSerial +
                "','serial':'" +
                record.serial +
                "','deliverySerial':'" +
                record.deliverySerial +
                "'}"
              }
              size={200} // 二维码的大小
              fgColor="#000000" // 二维码的颜色
              style={{ margin: 'auto', display: 'none' }}
            />
          </a>
        </>
      ),
    },
  ];

  // const QRModal = () => {
  //   const { id } = qrData as { id: number };
  //   return (
  //     <Modal
  //       width={400}
  //       footer={null}
  //       open={isModalOpen}
  //       onCancel={() => setIsModalOpen(false)}
  //     >
  //       <div
  //         style={{
  //           display: 'flex',
  //           flexDirection: 'column',
  //           alignItems: 'center',
  //         }}
  //       >

  //     </Modal>
  //   );
  // };

  const handleQrCode = (record) => {
    console.log('record', record);
    delivererVerification(record);
  };

  return (
    <div>
      <UploadQRCode onQrCode={handleQrCode} />
      {startScan && (
        <QrReader
          onResult={(result, error) => {
            if (result) {
              try {
                const a = JSON.parse(result.getText());
                console.log('aaa', a);
                const orderForVeri = {
                  account: currentUser.account,
                  userAccountSerial: a['userAccountSerial'],
                  serial: a['serial'],
                  deliverySerial: a['deliverySerial'],
                };
                console.log(orderForVeri);
                delivererVerification(orderForVeri);
              } catch (error) {
                console.error('Error parsing QR code data:', error);
              }
            }

            if (error) {
              console.info('error!', error);
            }
          }}
          containerStyle={{ width: '300px', height: '300px' }}
          scanDelay={3000}
          constraints={{ facingMode: 'user' }}
        />
      )}

      <QRcode
        id="1231"
        value="{'userAccountSerial':'1234','serial':'1231','deliverySerial':'123'}"
        size={200} // 二维码的大小
        fgColor="#000000" // 二维码的颜色
        style={{ margin: 'auto', display: 'none' }}
      />
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
              style={access.dispatcher ? {} : { display: 'none' }}
              onClick={() => handleCreateModalVisible(true)}
            >
              Create
            </Button>,
            <Button
              key="2"
              type="primary"
              style={access.deliverer ? {} : { display: 'none' }}
              onClick={() => {
                setStartScan(!startScan);
              }}
            >
              {startScan ? 'Stop Scan' : 'Start QR-code Scan'}
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
            {/* <ProFormSelect
            options={[
              { value: 0, label: 'Generated' },
              { value: 1, label: 'Signed' },
              { value: 2, label: 'Delivered' }
            ]}
            width="md"
            name="status"
            label='status'
          /> */}
            <ProFormText width="md" name="serial" label="Serial" />
            <ProFormText
              width="md"
              name="userAccountSerial"
              label="UserAccountSerial"
            />
            <ProFormText width="md" name="realName" label="RealName" />
            <ProFormText
              width="md"
              name="deliverySerial"
              label="DeliverySerial"
            />
            <ProFormText width="md" name="deliveryName" label="DeliveryName" />
            <ProFormText width="md" name="boxSerial" label="BoxSerial" />
          </ProForm>
        </BaseModal>
      </PageContainer>
      {/* {startScan && ( */}

      {/* )} */}
      {/* {data !== "" && <p>{data}</p>} */}
      {/* {dataJson !== "" && <p>{dataJson["account"]}</p>} */}
    </div>
  );
}
