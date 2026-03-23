<template>
  <div class="auth-container">
    <el-card class="auth-card" shadow="hover">
      <h2 class="auth-title">校园匿名墙 - 登录</h2>
      <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="loginForm.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading">登录</el-button>
          <el-button @click="$router.push('/register')">没有账号？去注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'
import { login } from '@/api/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const loginFormRef = ref()

const loginForm = reactive({
  studentId: '',
  password: ''
})

const loginRules = {
  studentId: [{ required: true, message: '学号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
}

const handleLogin = async () => {
  await loginFormRef.value.validate()
  loading.value = true
  try {
    const res = await login(loginForm)
    const token = res.data.data
    userStore.setToken(token)
    await userStore.getUserInfo()
    ElMessage.success('登录成功')
    router.push('/')
  } catch (err) {
    ElMessage.error(err.response?.data?.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f0f2f5;
}
.auth-card {
  width: 420px;
  padding: 30px;
}
.auth-title {
  text-align: center;
  margin-bottom: 24px;
  color: #409eff;
}
</style>