<template>
  <div class="common-layout">
    <el-container>
      <!--       TODO 登录页的图片展示区 -->
      <el-aside>
        <CityGate size="700" fill='#2a9dff' theme='outline'></CityGate>
      </el-aside>

      <el-main>
        <div class="login-form">
          <el-row :gutter="24">
            <el-col :span="3"></el-col>
            <el-col :span="9">
              <el-text class="active-text" type="primary">账户密码登录</el-text>
            </el-col>
            <el-col :span="3">

            </el-col>
            <el-col :span="6">
              <el-text class="active-text" type="info">验证码登录</el-text>
            </el-col>
            <el-col :span="3"></el-col>
          </el-row>
          <hr>
          <el-form
              ref="ruleFormRef"
              :model="ruleForm"
              status-icon
              :rules="rules"
              label-position="left"
          >
            <el-row :gutter="24">
              <el-col :span="18">
                <el-form-item label="账户&nbsp;&nbsp;&nbsp;&nbsp;" prop="account">
                  <el-input
                      v-model="ruleForm.account"
                      size="large"
                      show-word-limit
                      maxlength="20"
                      type="text"
                      autocomplete="off"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <!-- 暂时空出来-->
              </el-col>
            </el-row>

            <el-row :gutter="24">
              <el-col :span="18">
                <el-form-item label="密码&nbsp;&nbsp;&nbsp;&nbsp;" prop="passwd">
                  <el-input
                      v-model="ruleForm.passwd"
                      type="password"
                      size="large"
                      show-password
                      autocomplete="off"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <!--暂时空出来-->
              </el-col>
            </el-row>
            <el-row :gutter="24">
              <el-col :span="18">
                <el-form-item label="验证码" prop="verifyCode">
                  <el-input
                      v-model.number="ruleForm.verifyCode"
                      show-word-limit
                      maxlength="6"
                      size="large"/>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <!--暂时空出来-->
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="18">

              </el-col>
              <el-row>
                <el-link type="primary">忘记密码？</el-link>
              </el-row>
            </el-row>
            <br>
            <el-row :gutter="24">
              <el-col :span="2">

              </el-col>
              <el-col :span="18">
                <el-form-item>
                  &nbsp;
                  <el-button
                      style="width: 370px;color: #1890ff"
                      :disabled="disabledFlag"
                      type="primary"
                      size="large"
                      @click="doLogin(ruleFormRef)">登录
                  </el-button>
                </el-form-item>
              </el-col>
              <el-col>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script lang='ts' setup>
import {reactive, ref} from "vue";
import type {FormInstance, FormRules} from 'element-plus'

const ruleFormRef = ref<FormInstance>()

const ruleForm = reactive({
  // 账户密码
  account: '',
  passwd: '',
  // 验证码
  verifyCode: '',
  // 防重复提交信息
  uuid: ''
})

const rules = reactive<FormRules<typeof ruleForm>>({})


const disabledFlag = ref(true);

const doLogin = (form) => {
  console.log('doLogin', form)
}


</script>


<style lang='scss' scoped>

.login-form {
  border: 1px solid red;
  margin-top: 300px;
}

.el-aside {
  background: none;
  width: 60vw;
  height: 100vh;
  border: 1px blue solid;
}

.el-main {
  background: none;
  width: 40vw;
  height: 100vh;
}

.active-text {
  font-size: 25px;
}

</style>