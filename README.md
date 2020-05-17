## polar-im
### 技术选型
#### 后端
##### 基本框架 springboot 
##### 数据库 mysql
##### 数据库连接池 HikariPool(springboot默认)
##### ORM框架 MyBatis
##### 缓存 redis
##### token jwt
##### 对象存储七牛云+sm图床
### 项目结构
#### 父工程统一管理依赖版本
#### polar-common提供工具类相关和通用依赖
#### polar-jwt是封装好的一个springboot-starter
#### polar-storage提供对象存储的功能,目前实现了ftp/七牛云/sm图床
#### polar-im-server作为提供给前端接口的主要服务
#### polar-im-client-vue是用vue2.0版本搭建的前端服务