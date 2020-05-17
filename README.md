## polar-im

### 技术选型
#### 后端
##### 基本框架 springboot 
##### 通讯Netty->websocket
##### 数据库 mysql
##### 数据库连接池 HikariPool(springboot默认)
##### ORM框架 MyBatis
##### 缓存 redis
##### token jwt
##### 对象存储七牛云+sm图床
#### 前端 vue

### 项目结构
#### 父工程统一管理依赖版本
#### polar-common提供工具类相关和通用依赖
#### polar-jwt是封装好的一个springboot-starter
#### polar-storage提供对象存储的功能,目前实现了ftp/七牛云/sm图床
#### polar-im-server作为提供给前端接口的主要服务
#### polar-im-client-vue是用vue2.0版本搭建的前端服务

### 快速启动
#### clone到本地,在父polar-im目录下 mvn install,之后启动IMServer
#### 在polar-im-client-vue目录下运行 npm install,之后运行 npm run dev
#### 登录账号可以自己注册,也可以登录测试用的账号zhangsan/密码123
#### 登录账号可以自己注册,也可以登录测试用的账号lisi/密码123
