<template>
  <div class="home-container">
    <el-header>
      <div class="header-content">
        <span class="title">校园匿名墙</span>
        <div class="user-info">
          <span>欢迎，{{ userStore.userInfo?.studentName || '同学' }}</span>
          <el-button type="danger" @click="handleLogout">退出登录</el-button>
        </div>
      </div>
    </el-header>
    <el-main>
      <p>这里是匿名墙首页内容</p>
    </el-main>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const router = useRouter()

const handleLogout = () => {
  userStore.clearToken()
  ElMessage.success('已退出登录')
  router.push('/login')
}
</script>

<style scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #409eff;
  color: white;
  line-height: 60px;
}
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}
.title {
  font-size: 20px;
  font-weight: bold;
}
.user-info {
  display: flex;
  gap: 16px;
  align-items: center;
}
.el-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}
</style>