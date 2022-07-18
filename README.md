# hospital

> 一个简单的医院预约挂号管理系统。包括 小程序、web管理系统、服务端。

本项目基于[轻量级脚手架](https://github.com/YuJian95/base-service)，学习本项目前, 可先查看原项目相关文档。

如对你有所帮助，麻烦请 **star**、 **watch**、 **fork**，一键三连支持一下！

**相关项目**

| 项目           | 仓库                                       | 备注                                         |
| -------------- | ------------------------------------------ | -------------------------------------------- |
| hospital       | https://github.com/YuJian95/hospital       | 后端代码                                     |
| Hospital-wxapp | https://github.com/YuJian95/hospital-wxapp | （前端）微信小程序、支付宝小程序、H5应用代码 |
| Hospital-web   | https://github.com/YuJian95/hospital-web   | （前端）PC管理系统代码     

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

#### 小程序

![image.png](http://image.yujian95.cn/halo/image_1643879798008.png)
![image.png](http://image.yujian95.cn/halo/image_1643879818766.png)
![image.png](http://image.yujian95.cn/halo/image_1643879835630.png)
- [更多小程序运行图片](https://github.com/YuJian95/Product-Prototype/tree/master/hospital-appointment#小程序)

#### 管理系统

![image.png](http://image.yujian95.cn/halo/image_1643879859752.png)
![image.png](http://image.yujian95.cn/halo/image_1643879874985.png)
![image.png](http://image.yujian95.cn/halo/image_1643879886827.png)

更多效果可点击查看[/doc/demo.md](doc/demo.md)

### 部署步骤

点击查看[/doc/deploy.md](doc/deploy.md)

## 反馈

如有问题欢迎提交 Issue ，遇到问题可以通过我的网站[【blog.yujian95.cn】](https://blog.yujian95.cn)联系我。

**收费** ！！！ 技术咨询服务或微信小程序、Web项目开发、官网定制等需求，可加微信：**mo0052**（Mores），备注 项目开发 或 咨询服务。

欢迎大家关注我的公众号【编程图解】，学习更多开发技能，包括并不限于，Java、Python、前端等知识。

![微信搜索【编程图解】](doc/yujian95.jpg)
