// src/stores/user.js 正确内容
import { defineStore } from 'pinia'
import { getUserInfo } from '@/api/user'

export const useUserStore = defineStore('user', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        userInfo: null
    }),
    actions: {
        setToken(token) {
            this.token = token
            localStorage.setItem('token', token)
        },
        clearToken() {
            this.token = ''
            this.userInfo = null
            localStorage.removeItem('token')
        },
        async getUserInfo() {
            if (!this.token) return
            try {
                const res = await getUserInfo(this.token)
                this.userInfo = res.data.data
            } catch (err) {
                this.clearToken()
            }
        }
    }
})