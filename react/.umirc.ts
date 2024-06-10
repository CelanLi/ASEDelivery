import { defineConfig } from '@umijs/max';
import routes from './config/route';

export default defineConfig({
  locale: {
    default: 'en-US',
    antd: true,
    baseNavigator: true,
  },
  antd: {},
  access: {},
  model: {},
  initialState: {},
  request: {},
  layout: {
    title: 'Express Management',
  },
  routes,
  npmClient: 'yarn',
});
