export default (initialState: API.UserInfo) => {
  // define the authority based on the initial state
  // references: https://next.umijs.org/docs/max/access
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
