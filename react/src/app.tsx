import { RuntimeConfig } from 'umi';
import { history } from '@umijs/max';
import RightContent from '@/components/RightContent';
const loginPath = '/Login';

// 运行时配置

// 全局初始化数据配置，用于 Layout 用户信息和权限初始化
// 更多信息见文档：https://next.umijs.org/docs/api/runtime-config#getinitialstate
export async function getInitialState(): Promise<any> {
  // 获取用户初始化的信息
  return {};
}

export const layout: RuntimeConfig['layout'] = ({
  initialState,
  setInitialState,
}) => {
  return {
    onPageChange: () => {
      const { location } = history;
      console.log(
        'app~21 initialState?.currentUser：',
        initialState?.currentUser,
      );
      if (!initialState?.currentUser && location?.pathname !== loginPath) {
        history.push(loginPath);
      }
    },
    onMenuHeaderClick: () => {
      return false;
    },
    rightContentRender: () => <RightContent />,
    layout: 'mix',
  };
};
