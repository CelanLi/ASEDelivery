// 全局共享数据示例
import { useState } from 'react';
type INFO_TYPE = {
  name?: string;
  role: 'dispatcher' | 'customer' | 'employee';
};

const useUser = () => {
  const [userInfo, setUserInfo] = useState<INFO_TYPE>();
  return {
    userInfo,
    setUserInfo,
  };
};

export default useUser;
