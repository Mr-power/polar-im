import baseUrl from './baseUrl'
import axios from 'axios' // 引入 axios
import QS from 'qs' // 引入 qs
import store from '@/store'
import router from '../router'
// 创建 axios 实例
export const request = axios.create({
  baseURL: baseUrl, // api 的 base_url
  // 动态设置请求头
  // headers: {
  // Authorization: store.state.token
  // },
  timeout: 15000 // 请求超时时间
})

/*
  @ request 请求拦截器=>请求发出前处理
*/
request.interceptors.request.use(
  config => {
    if (config.method === 'post' || config.method === 'put') {
      // post请求时，序列化入参
      config.data = QS.stringify(config.data)
    }
    const token = store.getters.token
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  error => {
    // 接口返回失败
    console.log(error.response)
    Promise.reject(error.response)
  }
)

/*
  @ request 响应拦截器=>处理响应数据
*/
request.interceptors.response.use(
  response => {
    return Promise.resolve(response)
  },
  error => {
    // 请求失败，这个地方可以根据error.response.status统一处理一些界面逻辑，比如status为401未登录,可以进行重定向
    console.log(error)
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 未认证,清除当前缓存信息,跳转到登录页面
          router.push('/login')
      }
    }
    return Promise.reject(error.response)
  }
)
