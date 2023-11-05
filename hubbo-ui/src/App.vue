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
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>


<script lang='ts' setup>
import AsideBar from '@/components/AsideBar.vue'
import HeaderBar from '@/components/HeaderBar.vue'
</script>


<style lang='scss'>
body {
  width: 100vw;
  height: 100vh;
  color: white;
}

.el-header, .el-footer {
  background-color: #ffffff;
  border-right: none;
}

.el-aside {
  background-color: red;
  border-right: none;
}

.el-main {
  background-color: orange;
  color: #1a1a1a;
}

.el-header {
  height: 10vh;
  width: 100vw;
}

.el-aside {
  width: 10vw;
  height: 90vh;
}

.el-main {
  width: 90vw;
  height: 90vh;
}
</style>
