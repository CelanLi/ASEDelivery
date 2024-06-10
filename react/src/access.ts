export default (initialState: API.UserInfo) => {
  // 在这里按照初始化数据定义项目中的权限，统一Management
  // 参考文档 https://next.umijs.org/docs/max/access
  const role = initialState?.role || initialState?.currentUser?.role;

  const dispatcher = !!(role === 'dispatcher' || role === 'root');

  const customer = !!(role === 'customer' || role === 'root');

  const deliverer = !!(role === 'deliverer' || role === 'root');

  return {
    dispatcher,
    customer,
    deliverer,
  };
};
