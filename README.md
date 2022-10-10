# Deuteriun

这是一个基于SpringBoot和React而开发的前后端分离系统框架，主要组件包括：SpringBoot 、SpringSecurity、Mybatis-Plus、Redis、Gradle、React、Antd等
目前包含的功能：基本的认证登陆，鉴权，用户管理，用户注册，等

## 使用方法
-   [Deuteriun-api](https://github.com/Gavinin/Deuteriun/tree/master/Deuteriun-api) 为Java后端，[deuteriun-react](https://github.com/Gavinin/Deuteriun/tree/master/deuteriun-react)为后端
-   登录名：admin，密码：admin


# 技术栈
## 后端
-   Spring Boot 2.6.4
-   Mybatis 3.5.1
-   Spring Security 2.6.4
-   swagger 3

## 前端
- React

- React-router

- axios

- Antd

  


# 运行程序

- 前期配置：

  -   Resource目录下Application.yaml: 
      	secret-key
      	redis
      	mysql
  -   命令行方式运行：在项目根目录运行以下命令即可运行程序：


      mvn clean package
      cd deuteriun
      java -jar deuteriun/target/deuteriun-0.8.jar

  - 开发工具运行：在开发工具中运行DeuteriunApplication.java


# 待完成功能
1.  整体功能
    待完成：
    -   [ ] Demo页面
        -   [ ] 分页组件的使用
        -   [ ] 模态框组件的使用
        -   [ ] 图片预览组件的使用
        -   [ ] 文件上传下载组件的使用
        -   [ ] 字典组件的使用
        -   [ ] 按钮权限组件的使用
        -   [ ] WebSocket的使用
        -   [ ] 弹出框组件的使用
        -   [ ] 确认框组件的使用
        -   [ ] overlay组件的使用
        -   [ ] Element组件的使用
        -   [ ] 后端错误的展现
        -   [ ] 消息的发送以及显示
        -   [ ] 工作流的使用
        -   [ ] 日志功能的结束以及查询
        -   [ ] 代码生成工具的使用
    -   [X] 持续集成
    -	[ ] 点击列表名称进行排序
    -   [X] 添加表单验证
        -   [X] 前端数据校验
        -   [X] 后端数据校验
    -   [ ] 提供单独的建库脚本以及初始化数据脚本
    -   [X] 需要记录每个URL的访问耗时情况，统计起来以便优化
    -   [X] 封装参数验证以及异常处理
    -   [X] 代码生成
    -   [ ] 分模块，将工程根据不同的业务分为多个工程
    
    已完成： 
    -   [x] 进一步前后端分离
    -   [x] 搭建工作流服务
    -   [x] 拆分单点登录服务，管理多个系统
    -   [x] 引入Swagger3生成REST接口文档
    
2.  模块功能

	-   [X] 字典管理
	-   [X] 菜单管理
	-   [X] 角色管理
	-   [X] 用户管理
	-   [X] 日志查询
