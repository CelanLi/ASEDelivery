import mockjs from 'mockjs';
const { mock, Random } = mockjs;

export default {
  'GET /api/queryCourierList': (_req: any, res: any) => {
    res.json({
      success: true,
      data: mock({
        'list|100': [
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
