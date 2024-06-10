import mockjs from 'mockjs';
const { mock } = mockjs;

export default {
  'GET /api/queryBoxList': (_req: any, res: any) => {
    res.json({
      success: true,
      data: mock({
        'list|20': [
          {
            'id|+1': 1,
            address: '@province@city',
            'expressCount|1-99': 50,
          },
        ],
      }),
      errorCode: 0,
    });
  },
};
