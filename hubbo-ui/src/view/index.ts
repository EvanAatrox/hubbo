import {createRouter, createWebHistory, RouteRecordRaw} from "vue-router"


const baseRoutes: Array<RouteRecordRaw> = [{
    name: 'home',
    path: '/home',
    // @ts-ignore
    component: () => import('@/views/Home.vue')
},
    {
        name: 'space',
        path: "/space",
        // @ts-ignore
        component: () => import("@/views/other/Space.vue")
    },
    {
        name: 'notFound',
        path: '/404',
        // @ts-ignore
        component: () => import('@/views/system/NotFound.vue')
    }

]


const router = createRouter({
    history: createWebHistory(),
    routes: baseRoutes
});

export default router