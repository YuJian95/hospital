### 部署步骤

1. 运行 document文件夹下（mysql） `database.sql` 创建数据库 `base`

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

5. 运行 `BaseApplication.java`, 启动项目

6. 访问 API文档，运行后，范围 http://localhost:8080/base/doc.html ,输入`application-dev.yml`配置的账号密码即可

默认账号、密码为`base`

```yaml
## 开启 Swagger的 Basic认证功能,默认是false
swagger:
  # 是否关闭 swagger接口文档访问
  #  production: true
  basic:
    # 开启简单用户验证
    enable: true
    # 用户名
    username: base
    # 用户密码
    password: base
```