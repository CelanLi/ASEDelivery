import { Modal } from 'antd';
import React, { PropsWithChildren } from 'react';

interface BoxFormProps {
  modalVisible: boolean;
  onCancel?: () => void;
  title?: string;
}

const BoxForm: React.FC<PropsWithChildren<BoxFormProps>> = (props) => {
  const { modalVisible, onCancel, title = 'Create' } = props;

  return (
    <Modal
      destroyOnClose
      title={title}
      width={420}
      visible={modalVisible}
      onCancel={() => onCancel()}
      footer={null}
    >
      {props.children}
    </Modal>
  );
};

export default BoxForm;
