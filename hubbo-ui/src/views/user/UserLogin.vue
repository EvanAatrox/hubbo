<template>
    <div class='common-layout'>
        <el-container>
            <!--登录页的图片展示区 -->
            <el-aside class='login-aside'>
                <el-image class='login-background' src='/svg.png'></el-image>
            </el-aside>
            <!--登录的表单组件-->
            <el-main class='login-main'>
                <div class='login-form'>
                    <el-row :gutter='24'>
                        <el-col :span='2'></el-col>
                        <el-col :span='10'>
                            <el-text class='login-method' :class='usernamePasswdLoginMethod'
                                     @click='changeLoginMethod(0,$event)'>
                                账户密码登录
                            </el-text>
                        </el-col>
                        <el-col :span='2'>
                        </el-col>
                        <el-col :span='8'>
                            <el-text class='login-method ' type='info'
                                     :class='phonePasswdLoginMethod'
                                     @click='changeLoginMethod(1,$event)'>手机验证登录
                            </el-text>
                        </el-col>
                        <el-col :span='2'></el-col>
                    </el-row>
                    <br>
                    <hr>
                    <br>
                    <el-form
                        v-show='ruleForm.loginMethod === 0'
                        ref='ruleFormRef'
                        :model='ruleForm'
                        status-icon
                        :rules='rules'
                        label-position='left'
                    >
                        <el-row :gutter='24'>
                            <el-col :span='24'>
                                <el-form-item label='账户&nbsp;&nbsp;&nbsp;&nbsp;' prop='account'>
                                    <el-input
                                        v-model='ruleForm.account'
                                        size='large'
                                        show-word-limit
                                        maxlength='20'
                                        type='text'
                                        autocomplete='off' />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <br>
                        <br>
                        <el-row :gutter='24'>
                            <el-col :span='24'>
                                <el-form-item label='密码&nbsp;&nbsp;&nbsp;&nbsp;' prop='passwd'>
                                    <el-input
                                        v-model='ruleForm.passwd'
                                        type='password'
                                        size='large'
                                        show-password
                                        autocomplete='off'
                                    />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <br>
                        <br>
                        <el-row :gutter='24'>
                            <el-col :span='24'>
                                <el-form-item label='验证码' prop='verifyCode'>
                                    <el-input
                                        v-model.number='ruleForm.verifyCode'
                                        show-word-limit
                                        maxlength='6'
                                        size='large' />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <br>
                        <el-row>
                            <el-col :span='19'>
                            </el-col>
                            <el-col :span='5'>
                                <el-link type='primary'>忘记密码？</el-link>
                            </el-col>
                        </el-row>
                        <br>
                        <el-row :gutter='24'>
                            <el-col :span='3'></el-col>
                            <el-col :span='21'>
                                <el-form-item>
                                    <el-button
                                        style='width: 370px;color: #1890ff'
                                        :disabled='disabledFlag'
                                        type='primary'
                                        size='large'
                                        @click='doLogin(ruleFormRef)'>登录
                                    </el-button>
                                </el-form-item>
                            </el-col>
                            <el-col>
                            </el-col>
                        </el-row>
                    </el-form>
                    <el-form
                        v-show='ruleForm.loginMethod === 1'
                        ref='ruleFormRef'
                        :model='ruleForm'
                        status-icon
                        :rules='rules'
                        label-position='left'
                    >
                        <el-row :gutter='24'>
                            <el-col :span='24'>
                                <el-form-item label='手机号' prop='phone'>
                                    <el-input
                                        v-model='ruleForm.phone'
                                        size='large'
                                        show-word-limit
                                        maxlength='20'
                                        type='text'
                                        autocomplete='off' />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <br>
                        <br>
                        <el-row :gutter='24'>
                            <el-col :span='24'>
                                <el-form-item label='验证码' prop='phoneCode'>
                                    <el-input
                                        v-model.number='ruleForm.phoneCode'
                                        show-word-limit
                                        maxlength='6'
                                        size='large' />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <br>
                        <el-row :gutter='24'>
                            <el-col :span='3'></el-col>
                            <el-col :span='21'>
                                <el-form-item>
                                    <el-button
                                        style='width: 370px;color: #1890ff'
                                        :disabled='disabledFlag'
                                        type='primary'
                                        size='large'
                                        @click='doLogin(ruleFormRef)'>登录
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
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

// ---------------------------------校验区


/**
 *校验用户名
 * @param rule 校验规则
 * @param value dom值
 * @param callback 回调函数
 */
const checkUsername = (rule: any, value: any, callback: any) => {
    if (!value) {
        callback(new Error('用户名不允许为空'))
    } else {
        callback()
    }
}

/**
 *校验密码
 * @param rule 校验规则
 * @param value dom值
 * @param callback 回调函数
 */
const checkPasswd = (rule: any, value: any, callback: any) => {
    if (!value) {
        callback(new Error('密码不允许为空'))
    } else {
        callback()
    }
}


/**
 *校验验证码
 * @param rule 校验规则
 * @param value dom值
 * @param callback 回调函数
 */
const checkVerifyCode = (rule: any, value: any, callback: any) => {
    if (!value) {
        callback(new Error('验证码不允许为空'))
    } else {
        callback()
    }
}


/**
 *校验手机号
 * @param rule 校验规则
 * @param value dom值
 * @param callback 回调函数
 */
const checkPhone = (rule: any, value: any, callback: any) => {
    if (!value) {
        callback(new Error('手机号不允许为空'))
    } else {
        callback()
    }
}


/**
 *校验手机号验证码
 * @param rule 校验规则
 * @param value dom值
 * @param callback 回调函数
 */
const checkPhoneCode = (rule: any, value: any, callback: any) => {
    if (!value) {
        callback(new Error('验证码不允许为空'))
    } else {
        callback()
    }
}

// ---------------------------------

const ruleFormRef = ref<FormInstance>()

const ruleForm = reactive({
    // 用户名
    account: '',
    // 密码
    passwd: '',
    // 验证码
    verifyCode: '',
    // 防重复提交信息
    uuid: '',
    // 登录方式 0 用户名密码验证 1手机号 验证码登录
    loginMethod: 0,
    // 手机号
    phone: '',
    // 手机号的验证码
    phoneCode: '',
})


// 登录按钮的禁用标志位
const disabledFlag = ref(true)

//  校验规则
const rules = reactive<FormRules<typeof ruleForm>>({
    account: [{ validator: checkUsername, trigger: 'blur' }],
    passwd: [{ validator: checkPasswd, trigger: 'blur' }],
    verifyCode: [{ validator: checkVerifyCode, trigger: 'blur' }],
    phone: [{ validator: checkPhone, trigger: 'blur' }],
    phoneCode: [{ validator: checkPhoneCode, trigger: 'blur' }],
})


// 完成登录的方法
const doLogin = (form) => {
    console.log('doLogin', form)
}


// 登录方式文字高亮的class对象
const usernamePasswdLoginMethod = reactive({
    loginMethodActive: true,
})

const phonePasswdLoginMethod = reactive({
    loginMethodActive: false,
})


/**
 *
 * @param methodType 登录方式
 * @param event 事件源
 */
const changeLoginMethod = (methodType, event) => {
    // TODO 重置表单
    if (methodType === 1) {
        phonePasswdLoginMethod.loginMethodActive = true
        usernamePasswdLoginMethod.loginMethodActive = false
        ruleForm.loginMethod = 1
    } else {
        usernamePasswdLoginMethod.loginMethodActive = true
        phonePasswdLoginMethod.loginMethodActive = false
        ruleForm.loginMethod = 0
    }
}


</script>


<style lang='scss' scoped>

.login-aside {
    width: 55vw;
    height: 100vh;
}

.login-background {
    // 阻止滚动条的出现
    display: block;
    position: relative;
    width: 100%;
    height: 100%;
}

.login-main {
    position: relative;
    display: flex;
    padding-top: 200px;
    padding-left: 200px;
    background: #ffffff;
}

.login-method {
    font-size: 20px;
}

.loginMethodActive {
    color: #409eff;
}

</style>