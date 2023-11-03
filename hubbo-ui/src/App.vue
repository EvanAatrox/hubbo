<template>
  <router-view/>
</template>


<script lang='ts' setup>
import {ElMessage} from "element-plus";
import appStore from "@/store";

let clickFun = () => {
  ElMessage.success('ok')
}

console.log(appStore.globalStore.appName)
console.log(appStore.userStore.username)


</script>


<style lang='scss' scoped>
</style>
