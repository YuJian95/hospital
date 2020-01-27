### 简介

本项目是一个基于Spring boot + Mybatis，实现了日志记录与权限管理的，前后端分离的轻量级项目脚手架（后台部分）。

**项目功能**

- 简单的日志记录（账号登录日志，请求操作日志）
- 基于角色的动态访问权限管理。
- 在线 API文档，并实现简单的用户认证。

### 技术栈

- `Spring boot + Mybatis` ： 实现基于 RESTful 的前后端分离架构。
- `Spring security + JWT` ： 实现基于角色的动态权限管理。
- `Lombok` ： 减少了冗余代码。
- `Mybatis generator` ： 逆向生成相关实体类与配置文件。并且添加了相关 Swagger 注释。
- `Knife4j` ： 基于 Swagger + bootstrap 的 API 文档工具。 

### 相关说明

- [部署步骤说明](https://github.com/YuJian95/base-service/blob/master/document/deploy.md)
- [代码结构说明](https://github.com/YuJian95/base-service/blob/master/document/code-structure.md)
- [数据库设计说明](https://github.com/YuJian95/base-service/blob/master/document/database-table-structure.md)

### 参考项目

- [mall](https://github.com/macrozheng/mall)