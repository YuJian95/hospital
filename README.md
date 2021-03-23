# hospital

> 一个简单的医院预约挂号管理系统。包括 小程序、web管理系统、服务端。

本项目基于[轻量级脚手架](https://github.com/YuJian95/base-service)，学习本项目前, 可先查看原项目相关文档。

如对你有所帮助，麻烦请 **star**、 **watch**、 **fork**，一键三连支持一下！

## 前端代码仓库

- [小程序代码](https://gitee.com/yyyangyx/appointment-wxapp)
- [web管理系统代码](https://gitee.com/yyyangyx/appointment-admin)

### 主要技术

- `Spring boot + Mybatis` ： 实现基于 `RESTful` 的前后端分离架构。
- `Spring Security + JWT` ： 实现基于角色的动态权限管理。
- `Mybatis generator` ： 逆向生成相关实体类与配置文件，并且添加了相关 Swagger 注释。
- `Knife4j` ： 基于 Swagger + bootstrap 的 API 文档工具。 
- `阿里云短信服务` : 实现短信发送功能。
- `七牛云文件存储服务`: 取代自建的FTP图片服务器，实现图片存储便宜稳定。
- `Lombok` ： 减少了冗余的 Getter / Setter 代码。
- `Redis` ：实现二级缓存，提高响应速度。

### 软件界面

![小程序主要界面1](http://yujian95.cn/post/min-program-hospital-appointment/wx-1.png)
![管理系统主要界面1](http://yujian95.cn/post/min-program-hospital-appointment/admin-1.png)

更多效果可点击查看[/doc/demo.md](doc/demo.md)

### 部署步骤

点击查看[/doc/deploy.md](doc/deploy.md)

### 反馈

如有问题欢迎提交 Issue ，遇到问题可以通过我的网站[【yujian95.cn】](https://yujian95.cn/post/about.html)联系我。

因大家询问 **是否有可以讨论与交流的地方**，~~我建了个 Q 群：**866724245**~~（工作比较忙，不是很有空上QQ，建议大家提 issue。)。

入群门票： **star、fork、watch** 本项目，并备注自己的`GitHub` 账号名称。

欢迎大家关注我的公众号【编程图解】，学习更多开发技能，包括并不限于，Java、Python、前端等知识。

![微信搜索【编程图解】](doc/yujian95.jpg)

### TODO

正计划使用 [little 开发脚手架](https://github.com/YuJian95/little.git) 重构该项目...
