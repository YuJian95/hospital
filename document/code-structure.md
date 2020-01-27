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
        database.sql 建库脚本 sql

```

### 代码规范

- 遵照 《阿里巴巴 Java 开发手册》
- 接口满足 RESTful 规范。