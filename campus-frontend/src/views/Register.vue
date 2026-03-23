<template>
  <div class="auth-container">
    <el-card class="auth-card" shadow="hover">
      <h2 class="auth-title">校园匿名墙 - 注册</h2>
      <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" label-width="80px">
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="registerForm.studentId" placeholder="请输入学号" />
        </el-form-item>
        <el-form-item label="姓名" prop="studentName">
          <el-input v-model="registerForm.studentName" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="至少6位密码" show-password />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="再次输入密码" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister" :loading="loading">注册</el-button>
          <el-button @click="$router.push('/login')">已有账号？去登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { register } from '@/api/user'

const router = useRouter()
const loading = ref(false)
const registerFormRef = ref()

const registerForm = reactive({
  studentId: '',
  studentName: '',
  password: '',
  confirmPassword: ''
})

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== registerForm.password) callback(new Error('两次密码不一致'))
  else callback()
}

const registerRules = {
  studentId: [{ required: true, message: '学号不能为空', trigger: 'blur' }],
  studentName: [{ required: true, message: '姓名不能为空', trigger: 'blur' }],
  password: [
    { required: true, message: '密码不能为空', trigger: 'blur' },
    { min: 6, message: '密码长度不少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  await registerFormRef.value.validate()
  loading.value = true
  try {
    await register({
      studentId: registerForm.studentId,
      studentName: registerForm.studentName,
      password: registerForm.password
    })
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (err) {
    ElMessage.error(err.response?.data?.message || '注册失败')
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