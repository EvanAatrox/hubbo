<template>
  <div class='common-layout'>
    <el-container>
      <el-header>
        <HeaderBar/>
      </el-header>
      <el-container>
        <el-aside>
          <AsideBar/>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script setup lang='ts'>

import AsideBar from "@/components/AsideBar.vue";
import HeaderBar from "@/components/HeaderBar.vue";
</script>
<style scoped lang='scss'>


</style>