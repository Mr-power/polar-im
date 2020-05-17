let baseUrl = ''
switch (process.env.NODE_ENV) {
  case 'development':
    baseUrl = 'http://localhost:9080/' // 开发环境url
    break
  case 'production':
    baseUrl = 'http://polar_web:8088/' // 生产环境url
    break
}

export default baseUrl
