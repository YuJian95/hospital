# 部署方式

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