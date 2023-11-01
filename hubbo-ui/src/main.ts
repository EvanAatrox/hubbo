import {createApp} from 'vue'
import elementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from '@/App.vue'
import zhCn from 'element-plus/dist/locale/zh-cn.js'
/*  引入icon svg插件需要配置代码 */
import 'virtual:svg-icons-register'
/* 引入全局样式 */
import '@/styles/index.scss'

const app = createApp(App)

app.use(elementPlus, {
    locale: zhCn,
})

console.log(import.meta.env)
app.mount('#app')
