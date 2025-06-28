<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.wang.supermarket.HelloServlet" %>
<html>
<head>
    <title>登陆成功</title>
</head>
<body>
    <h2>登陆成功</h2>
    <h3>欢迎您
        用户名：${user.username}
        角色：${user.role==1?'经理':'普通用户'}
    </h3>
</body>
</html>
