import mockjs from 'mockjs';
const { mock, Random } = mockjs;

export default {
  'GET /api/v1/queryUser': (req: any, res: any) => {
    res.json({
      success: true,
      data: mock({
        name: () => Random.cname(),
        'authority|1': ['admin', 'customer', 'employee'],
      }),
      errorCode: 0,
    });
  },
  'GET /api/v1/queryUserList': (req: any, res: any) => {
    res.json({
      success: true,
      data: mock({
        'list|20': [
          {
            'id|+1': 1,
            address: '@province@city',
            name: () => Random.cname(),
          },
        ],
      }),
      errorCode: 0,
    });
  },
};
