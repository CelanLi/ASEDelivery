const routes = [
  {
    path: '/',
  },
  {
    name: 'Boxes Management',
    path: '/box-manage',
    component: './BoxManage',
    access: 'dispatcher',
  },
 /*  {
    name: 'Deliverer Management',
    path: '/courier-manage',
    component: './CourierManage',
    access: 'dispatcher',
  },
  {
    name: 'Customer Management',
    path: '/users-manage',
    component: './UesrsManage',
    access: 'dispatcher',
  }, */
  {
    name: 'Orders Management',
    path: '/order-manage',
    component: './OrderManage',
    access: 'dispatcher',
  },
  {
    name: 'History Orders',
    path: '/old-order',
    component: './OldOrder',
    access: 'customer',
  },
  {
    name: 'New Orders',
    path: '/new-order',
    component: './NewOrder',
    access: 'customer',
  },
  {
    name: 'Boxes Management',
    path: '/courier-box-manage',
    component: './BoxManage',
    access: 'deliverer',
  },
  {
    name: 'Orders Management',
    path: '/courier-order-manage',
    component: './OrderManage',
    access: 'deliverer',
  },
  {
    name: 'Account Management',
    path: '/account-manage',
    component: './AccountManage',
    access: 'dispatcher',
  },
  {
    name: 'Login',
    path: '/login',
    component: './Login',
    layout: false,
  },
];

export default routes;
