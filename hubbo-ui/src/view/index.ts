import {createRouter, createWebHistory, NavigationGuardNext, RouteLocationNormalized,} from 'vue-router'

import {checkHasPermission} from '@/utils/permissionUtils.ts'
import {error} from '@/utils/messageUtils.ts'
import getBaseRoutes from '@/view/baseRoutes.ts'


const router = createRouter({
    history: createWebHistory(),
    routes: getBaseRoutes(),
})

// 前置路由守卫
router.beforeEach((to: RouteLocationNormalized, from: RouteLocationNormalized, next: NavigationGuardNext) => {
    if (to.path === '/login' || !to.meta.auth) {
        next()
    } else if (checkHasPermission()) {
        next()
    } else {
        error('无权限访问,即将前往登录页')
        // 跳转登录页
        setTimeout(() => {
            router.push({
                path: '/user/login',
            }).then(r => {
                console.log('error', r)
            })
        }, 2000)
    }
})

// 后置守卫,刷新网站的title
router.afterEach((to) => {
    if (to.meta && to.meta.title) {
        // @ts-ignore
        document.title = to.meta.title
    }
})

export default router