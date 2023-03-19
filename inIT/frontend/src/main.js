import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import './style.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/display.css'


createApp(App)
  .use(router)
  .use(store)
  .use(ElementPlus)
  .mount('#app');
