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
            // @ts-ignore
            component: () => import('@/views/user/UserLogin.vue'),
            meta: {
                auth: false,
                title: '登录',
            },
        },
        {
            name: 'encryptTest',
            path: '/encrypt/test',
            // @ts-ignore
            component: () => import('@/views/other/EncryptTest.vue'),
            meta: {
                auth: false,
                title: '加密测试',
            },
        },
    ]
    return baseRoutes
}