import {createApp} from 'vue'
import elementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from '@/App.vue'
import zhCn from 'element-plus/dist/locale/zh-cn.js'
/*  引入icon svg插件需要配置代码 */
import 'virtual:svg-icons-register'
/* 引入全局样式 */
import '@/styles/index.scss'
import {createPinia} from "pinia"
import {initialStore} from "@/store"
import router from "@/view";

const app = createApp(App)
// 挂载store
app.use(createPinia())
app.use(router)
// 国际化，可有可无
app.use(elementPlus, {
    locale: zhCn,
})
// 初始化Pinia信息
initialStore()
// console.log(import.meta.env)
app.mount('#app')
