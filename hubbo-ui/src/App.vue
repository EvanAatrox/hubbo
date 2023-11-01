<template>
  <h1 style='color: blue'>hello Vue</h1>
  <el-link type='primary' @click='clickFun()'>hello</el-link>
  <br>
  <SvgComponent :style-obj='{width:800,height:800}' color='blue' name='space'
                style='display: flex;margin-left: 30%'></SvgComponent>
</template>


<script lang='ts' setup>
import SvgComponent from '@/components/icons/SvgComponent.vue'
import {ElMessage} from 'element-plus'


let clickFun = () => {
  ElMessage.success('ok')
}

</script>


<style lang='scss' scoped>
</style>
