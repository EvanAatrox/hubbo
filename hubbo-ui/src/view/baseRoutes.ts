import {RouteRecordRaw} from 'vue-router'

export default function getDefaultRoutes() {

    const baseRoutes: Array<RouteRecordRaw> = [{
        name: 'home',
        path: '/',
        // @ts-ignore
        component: () => import('@/views/Home.vue'),
        meta: {
            auth: false,
            title: '首页',
        },
    },
        {
            name: 'space',
            path: '/space',
            // @ts-ignore
            component: () => import('@/views/other/Space.vue'),
            meta: {
                auth: true,
                title: '太空人',
            },
        },
        {
            name: 'notFound',
            path: '/404',
            // @ts-ignore
            component: () => import('@/views/system/NotFound.vue'),
            meta: {
                auth: false,
                title: '资源不见了',
            },
        },
        {
            name: 'login',
            path: '/user/login',
            component: () => import('@/views/user/UserLogin.vue'),
            meta: {
                auth: false,
                title: '登录',
            },
        },
    ]
    return baseRoutes
}