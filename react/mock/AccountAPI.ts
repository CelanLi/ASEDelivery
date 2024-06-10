import mockjs from 'mockjs';
const { mock, Random } = mockjs;

export default {
  'GET /api/queryAccountList': (_req: any, res: any) => {
    res.json({
      success: true,
      data: mock({
        'list|10': [
          {
            account: '@province@city',
            password: () => Random.cname(),
            'type|1': ['deliverer', 'customer'],
          },
        ],
      }),
      errorCode: 0,
    });
  },
};
