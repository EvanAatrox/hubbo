<template>
  <router-view/>
</template>


<script lang='ts' setup>
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
  background-color: blue;
  border-right: none;
}

.el-main {
  background-color: yellow;
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
