### 简介

> 一个简单的医院预约挂号管理系统。包括 小程序、web管理系统、服务端。

如对你有所帮助，麻烦请 **star**、 **watch**、 **fork**，一键三连支持一下！

- [服务端](https://github.com/YuJian95/hospital-service)
- [小程序端](https://gitee.com/yyyangyx/appointment-wxapp)
- [web管理系统](https://gitee.com/yyyangyx/appointment-admin)

### 软件界面

- [小程序](https://github.com/YuJian95/Product-Prototype/tree/master/hospital-appointment#%E5%B0%8F%E7%A8%8B%E5%BA%8F)

- [web管理系统](https://github.com/YuJian95/Product-Prototype/tree/master/hospital-appointment#web%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F)

### 主要技术

- `Spring boot + Mybatis` ： 实现基于 RESTful 的前后端分离架构。
- `Spring security + JWT` ： 实现基于角色的动态权限管理。
- `Mybatis generator` ： 逆向生成相关实体类与配置文件。并且添加了相关 Swagger 注释。
- `Knife4j` ： 基于 Swagger + bootstrap 的 API 文档工具。 
- `阿里云短信服务` : 实现短信发送验证码功能
- `七牛云文件存储服务`: 实现图片存储
- `Lombok` ： 减少了冗余代码。
- `Redis` ：实现二级缓存。

### 部署步骤

1. 运行 resource 文件夹下（mysql） `hospital.sql` 创建数据库 `base`

2. 修改 `database.properties`中数据库相关配置

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://数据库url:端口/数据库名
jdbc.username=数据库用户名
jdbc.password=数据库用户密码
```

3. 修改 `application-dev.yml`中数据库相关配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://数据库url:端口/数据库名?characterEncoding=UTF-8&useSSL=false&useUnicode=true&serverTimezone=UTC
    username: 数据库用户名
    password: 用户密码
```

4. 配置 `redis`和 阿里云短信服务 

修改 host 和 password

```yaml
redis:
    # Redis服务器地址
    host: localhost
    # Redis数据库索引（默认为0）
    database: 0
    # Redis服务器连接端口
    port: 6379
    # Redis密码
    password: password
```

**阿里云短信服务**

修改 accessKeyId，accessSecret，signNam，loginTemplate

```yaml
# 阿里云短信服务配置
aliSms:
  # 阿里云短信服务 key
  accessKeyId: ""
  # 阿里云短信服务 secret
  accessSecret: ""
  # 短信抬头
  signName: ""
  # 注册短信验证码模板
  loginTemplate: "SMS_"
  regionId: "cn-hangzhou"
  domain: "dysmsapi.aliyuncs.com"
  version: "2017-05-25"
```

5. 配置七牛云文件存储

```yaml
# 七牛云文件存储
qiniu:
  accessKey: 
  secretKey: 
  bucket: 存储空间名称
  # 访问域名前缀  
  url: http://q59ifzu6u.bkt.clouddn.com/
```

6. 运行 `HospitalApplication.java`, 启动项目

7. 访问 API文档，运行后，范围 http://localhost:8080/hospital/doc.html ,输入`application-dev.yml`配置的账号密码即可

默认账号、密码为`hospital`

```yaml
## 开启 Swagger的 Basic认证功能,默认是false
swagger:
  # 是否关闭 swagger接口文档访问
  #  production: true
  basic:
    # 开启简单用户验证
    enable: true
    # 用户名
    username: hospital
    # 用户密码
    password: hospital
```