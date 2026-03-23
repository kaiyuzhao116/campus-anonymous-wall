import axios from 'axios'

const request = axios.create({
    baseURL: 'http://localhost:8001', // 后端服务地址
    timeout: 5000
})

// 注册
export const register = (data) => {
    return request({
        url: '/auth/register',
        method: 'POST',
        data
    })
}

// 登录
export const login = (data) => {
    return request({
        url: '/auth/login',
        method: 'POST',
        data
    })
}

// 获取用户信息
export const getUserInfo = (token) => {
    return request({
        url: '/auth/user-info',
        method: 'POST',
        params: { token }
    })
}