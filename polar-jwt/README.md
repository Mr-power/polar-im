## polar-jwt-starter

### JWT starter

#### 调用方式:

##### 1.添加依赖
##### 2.入口类上添加 @EnableJWT 注解
##### 3.配置文件增加配置项
``
jwt:
  // JWT秘钥
  secret: JustWannaU
  // JWT有效期（毫秒），默认15天
  expiration: 1296000000
  // 加密算法
  signature-algorithm-name: HS512
  // 不需要验证的请求路径
  exclude-path-patterns: /**/login, /login, /login/*
``
##### 4.调用 jwtUtils 生成token
##### 5.请求时,将 token 放到 header Authorization 字段中 
 
