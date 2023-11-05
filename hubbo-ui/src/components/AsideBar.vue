<template>
  <!--    <el-radio-group v-model='disabledFlag' style='margin-bottom: 20px'>-->
  <!--        <el-radio-button :label='false'>展开</el-radio-button>-->
  <!--        <el-radio-button :label='true'>折叠</el-radio-button>-->
  <!--    </el-radio-group>-->
  <el-menu
      :collapse='disabledFlag'
      class='el-menu-vertical-demo'
      default-active='0'
      @close='handleClose'
      @open='handleOpen'
  >
    <el-sub-menu index='0'>
      <template #title>
        <el-icon>
          <ApiApp fill='#2a9dff' theme='outline'/>
        </el-icon>
        <span>系统栏</span>
      </template>
      <el-menu-item-group>
        <template #title>
          系统相关
        </template>
        <el-menu-item index='0-1'>
          <template #title>
            <Announcement></Announcement>
            系统公告
          </template>

        </el-menu-item>
        <el-menu-item index='0-2'>
          <template #title>
            <MonitorCamera></MonitorCamera>
            系统看板
          </template>

        </el-menu-item>
      </el-menu-item-group>

    </el-sub-menu>
    <el-sub-menu index='1'>
      <template #title>
        <el-icon>
          <AssemblyLine fill='#2a9dff' theme='outline'/>
        </el-icon>
        <span>开发任务</span>
      </template>
      <el-menu-item-group>
        <template #title><span>系统相关</span></template>
        <el-menu-item index='1-1'>
          <template #title>
            <ListTwo></ListTwo>
            待开发需求
          </template>

        </el-menu-item>
        <el-menu-item index='1-2'>
          <template #title>
            <PullRequests></PullRequests>
            Git日志
          </template>
        </el-menu-item>
      </el-menu-item-group>
    </el-sub-menu>
  </el-menu>
</template>

<script lang='ts' setup>
import {ref} from 'vue'

const disabledFlag = ref(false)

const handleOpen = () => {
  console.log('open')
}

const handleClose = () => {
  console.log('close')
}

</script>

<style lang='scss' scoped>

</style>