<template>
    <div class='common-layout'>
        <el-container>
            <el-header class='header'>
                <HeaderBar />
            </el-header>
            <el-container>
                <el-aside class='aside'>
                    <AsideBar />
                </el-aside>
                <el-main class='main'>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script setup lang='ts'>

import AsideBar from '@/components/AsideBar.vue'
import HeaderBar from '@/components/HeaderBar.vue'
</script>
<style scoped lang='scss'>


</style>