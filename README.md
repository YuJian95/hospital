### 简介

本项目是一个基于Spring boot + Mybatis，包括日志记录与权限管理的简单项目脚手架。

### 技术栈

- `Spring boot + Mybatis` ： 实现基于 RESTful 的前后端分离架构。
- `Spring security + JWT` ： 实现基于 RBAC 的动态权限管理。
- `Lombok` ： 减少了冗余代码。
- `Mybatis generator` ： 逆向生成相关实体类与配置文件。并且添加了相关 Swagger 注释。
- `Knife4j` ： 基于 Swagger + bootstrap 的 API 文档工具。 

### 目录结构

```
base-service
├── main
    ├── java
        ├── cn.yujian95.base
            ├── common 通用类
                ├── api API接口相关类
                ├── mbg Mybatis 逆向工具类
                ├── security Spring Security 相关类
            ├── component 通用组件
            ├── config 配置类
            ├── controller 控制器类
            ├── dto 数据传输对象
                ├── param 数据传入参数对象
            ├── entity Mybatis 逆向生成的实体类
            ├── mapper Mybatis 逆向生成的 mapper接口（DAO）
                ├── dao 个人编写的 mapper接口
            ├── service 业务接口类
                ├── impl 业务接口实现类
            BaseApplication 应用启动类

    ├── resource 配置资源
        ├── cn.yujian95.base.mapper mbg 生成的映射配置文件
            ├── dao 个人编写的映射配置文件
        ├── config 配置文件
            MybatisGeneratorConfig.xml Mybatis 逆向工程配置
            SqlMapConfig.xml 分页工具 pagehelper 数据库类型配置
        application-dev.yml 应用相关配置
        database.properties 数据库配置

```

### 代码规范

- 遵照 《阿里巴巴 Java 开发手册》
- 接口满足 RESTful规范。

### 部署步骤

1. 运行 `database.sql` 创建数据库

2. 修改 `database.properties`中数据库相关配置

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://数据库url:端口/数据库名
jdbc.username=数据库用户名
jdbc.password=数据库用户密码
```

3. 修改 `application-dev.yml`中数据库相关配置

```yml
spring:
  datasource:
    url: jdbc:mysql://数据库url:端口/数据库名?characterEncoding=UTF-8&useSSL=false&useUnicode=true&serverTimezone=UTC
    username: 数据库用户名
    password: 用户密码
```

### 参考项目

- [mall](https://github.com/macrozheng/mall)