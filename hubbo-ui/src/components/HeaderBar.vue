<template>
  <el-row>
    <el-col :span='1'>
      <el-input v-model='searchInput'
                autofocus
                class='search'
                fill='#333'
                placeholder='Search Docs'
                prefix-icon='Search'
                size='large'
                suffix-icon='EnterKey'
                theme='outline'
                type='text'
                @blur='doSearch'
      />
    </el-col>
    <el-col :span='1'>
      <el-tooltip :content='tip' placement='top'>
        <el-switch
            v-model='darkStatus'
            :loading='loadingStatus'
            active-icon='SunOne'
            active-value='1'
            class='switch'
            inactive-icon='Moon'
            inactive-value='0'
            inline-prompt
            size='large'
            style='--el-switch-on-color: #f2f2f2; --el-switch-off-color: #2c2c2c'
            @change='changeThemeMode'
        />
      </el-tooltip>
    </el-col>
    <el-col :span='1'>
      <div class='avatar'>
        <el-avatar :size='50' :src='circleUrl'/>
      </div>
    </el-col>
  </el-row>
</template>
<script lang='ts' setup>
import {reactive, ref, toRefs, watch} from 'vue'

const state = reactive({
  circleUrl: 'http://192.168.10.39:9000/api/alien/download/90210bfd-ffcf-4aa8-754a-f98347ca4cf3/blue-eye.png',
})

const themeObj = reactive({
  darkStatus: 0,
  tip: '',
  loadingStatus: false,
  initialVal: 0,
})

const {circleUrl} = toRefs(state)

const {darkStatus, tip, loadingStatus} = toRefs(themeObj)

// 监控黑夜模式开关状态的切换
watch(themeObj, (newValue, oldValue, onCleanup) => {
  const status = parseInt(newValue.darkStatus)
  if (status === 1) {
    themeObj.tip = '关闭黑夜模式'
  } else {
    themeObj.tip = '切换到黑夜模式'
  }
}, {immediate: false})

// 监控黑夜模式开关的变化并可在此过程中加载动画
const changeThemeMode = (val: number) => {
  themeObj.loadingStatus = true
  setTimeout(() => {
    themeObj.loadingStatus = false
  }, 2000)
}

const searchInput = ref('')


const doSearch = () => {
  console.log('doSearch', searchInput.value)
}

</script>

<style lang='scss' scoped>
.avatar {
  margin-top: 15px;
  margin-left: 90vw;
}

.switch {
  margin-top: 22px;
  margin-left: 90vw;
}

.search {
  margin-top: 14px;
  width: 250px;
  height: 50px;
}

</style>
