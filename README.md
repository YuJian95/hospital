## 简介

> 一个简单的医院预约挂号管理系统。包括 小程序、web管理系统、服务端。

本项目基于[轻量级脚手架](https://github.com/YuJian95/base-service)，学习本项目前, 可先查看原项目相关文档。

如对你有所帮助，麻烦请 **star**、 **watch**、 **fork**，一键三连支持一下！

- [服务端](https://github.com/YuJian95/hospital)
- [小程序端](https://gitee.com/yyyangyx/appointment-wxapp)
- [web管理系统](https://gitee.com/yyyangyx/appointment-admin)

### 主要技术

- `Spring boot + Mybatis` ： 实现基于 RESTful 的前后端分离架构。
- `Spring Security + JWT` ： 实现基于角色的动态权限管理。
- `Mybatis generator` ： 逆向生成相关实体类与配置文件，并且添加了相关 Swagger 注释。
- `Knife4j` ： 基于 Swagger + bootstrap 的 API 文档工具。 
- `阿里云短信服务` : 实现短信发送功能。
- `七牛云文件存储服务`: 取代自建的FTP图片服务器，实现图片存储便宜稳定。
- `Lombok` ： 减少了冗余的 Getter / Setter 代码。
- `Redis` ：实现二级缓存，提高响应速度。

### 软件界面

#### 小程序

![小程序主要界面1](http://yujian95.cn/post/min-program-hospital-appointment/wx-1.png)
![小程序主要界面2](http://yujian95.cn/post/min-program-hospital-appointment/wx-2.png)
![小程序主要界面3](http://yujian95.cn/post/min-program-hospital-appointment/wx-3.png)

- [更多小程序运行图片](https://github.com/YuJian95/Product-Prototype/tree/master/hospital-appointment#%E5%B0%8F%E7%A8%8B%E5%BA%8F)

#### 管理系统

![管理系统主要界面1](http://yujian95.cn/post/min-program-hospital-appointment/admin-1.png)
![管理系统主要界面2](http://yujian95.cn/post/min-program-hospital-appointment/admin-2.png)
![管理系统主要界面3](http://yujian95.cn/post/min-program-hospital-appointment/admin-3.png)

- [更多web管理系统图片](https://github.com/YuJian95/Product-Prototype/tree/master/hospital-appointment#web%E7%AE%A1%E7%90%86%E7%B3%BB%E7%BB%9F)

### 部署步骤

1. 运行 /src/main/resource 文件夹下（mysql数据库脚本） `hospital.sql` 创建数据库 `hospital`

2. 修改 `database.properties`中数据库相关配置

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://数据库url:端口/hospital
jdbc.username=数据库用户名
jdbc.password=数据库用户密码
```

3. 修改 `application.yml`中数据库相关配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://数据库url:端口/hospital?characterEncoding=UTF-8&useSSL=false&useUnicode=true&serverTimezone=UTC
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

5. 阿里云短信服务

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

6. 配置七牛云文件存储

```yaml
# 七牛云文件存储
qiniu:
  accessKey: 
  secretKey: 
  bucket: 存储空间名称
  # 访问域名前缀  
  url: http://xxx.bkt.clouddn.com/
```

7. 运行 `HospitalApplication.java`, 启动项目

8. 访问 API文档，运行后，范围 http://localhost:8080/hospital/doc.html ,输入`application.yml`配置的账号密码即可

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

9. 系统账号，这里提供 3 种角色账号。

```
// 管理系统 - 管理员
账号：admin
密码：admin

// 管理系统 - 医生
账号：doctor
密码：123456

// API 接口测试账号
账号：test
密码：test
```

### 反馈

如有问题欢迎提交 Issue ，遇到问题可以通过[我的博客](https://yujian95.cn/post/about.html)联系我。

因大家询问**是否有可以讨论与交流的地方**，我建了个 Q 群：**866724245**。

入群门票：**star、fork、watch**本项目，并备注 `GitHub` 账号名称。