import mockjs from 'mockjs';
const { mock, Random } = mockjs;

export default {
  'GET /api/queryOrderList': (_req: any, res: any) => {
    res.json({
      success: true,
      data: mock({
        'list|100': [
          {
            'id|+1': 1,
            'status|1': [0, 1, 2],
            customer: () => Random.cname(),
            deliverer: () => Random.cname(),
            'box|1-99': 50,
          },
        ],
      }),
      errorCode: 0,
    });
  },
};
