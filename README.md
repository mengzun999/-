web协议和核心

1. web请求方式
2. Servlet的生命周期和实现方式
3. tomcat
4. JSP
5. Filter的使用
6. JDBC以及优化
7. 三层架构和DAO模式
8. JSTL的使用

maven: 中文：行家
作用：一个项目的依赖、生命周期、测试、打包和部署等管理工具

使用的过程： jar--》本地仓库--》中央仓库（国外）
配置maven的环境变量：

   MAVEN_HOME
   D:\java-sort\apache-maven-3.9.6

添加到path路径中：
   %MAVEN_HOME%\bin

 测试：  mvn -v
 如果输出对应的版本号即可

JDBC: 是java 数据库连接技术（SUN公司推出的一个数据库连接接口：由
各大数据库厂商去实现该接口：以jar包的方式）

JDBC的核心API(Applicaion Program Interface:应用程序编程接口)

1：连接对象：Connection（负责java与数据库进行连接）
2：执行对象：PreparedStatement(负责执行sql语句)
3：结果集：Resultset（负责封装查询结果的）

核心操作：
   PreparedStatement:
    executeUpdate(增、删、改)-->int: 受影响的行数
    executeQuery（查询语句）：--》ResultSet

登录的业务流程：
用户的登录页面输入账号和密码--》登录按钮-->触发action指定的url
--》helloservlet--> 
service方法执行：获取账号和密码（reuqest.getParamter(名称)）-->
调用数据库的查询语句并将账号和密码作为参数；
查询成功（返回一个对象）--》封装到session中并转发到成功的页面
查询失败（账号或密码错误）--》封装错误消息--》转发到登录页面。

1、三层架构和MVC模式

   a  表示（视图、界面）层 作用：展示数据
   b  业务层： 处理数据
   c  数据层： 进行数据库的操作

   表示层 --调用-》业务层--调用--》数据层

   数据层提供服务给业务层
   业务层提供服务给页面层

   DAO: Data Access Object
        数据  访问  对象

在数据层定义sql语句的好处：
安全：因为接口中的字段是final的，且不能被修改。
1、实现权限菜单：
    使用session和jstl实现用户角色动态显示（左侧菜单）。

2、使用java的正则和jQuery的离焦事件实现新增用户时数据的有效性的验证。

3、将每个请求封装到独立的方法中从而实现对Servlet的优化。



| 访问级别            | 本类 | 同包 | 继承关系 | 外部 |
| ------------------- | ---- | ---- | -------- | ---- |
| private(私有)       | yes  | no   | no       | no   |
| default(默认)       | yes  | yes  | no       | no   |
| protected（受保护） | yes  | yes  | yes      | no   |
| public(公共)        | yes  | yes  | yes      | yes  |
|                     |      |      |          |      |



| 区别     | 重定向   | 数据转发   |
| -------- | -------- | ---------- |
| 外部资源 | 可以     | 不可以     |
| 地址栏   | 发生变化 | 不发生变化 |
| 请求次数 | 多次请求 | 一次请求   |
| 传递数据 | 不能     | 可以       |
|          |          |            |

先用supermarket_db.sql创建数据库

连接数据库在src\main\resources\db.properties文件夹里添加密码
