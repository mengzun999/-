<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link type="text/css" rel="stylesheet" href="css/style.css" />
    <style>
        .welcome {
        text-align: end;
        margin-right: 40px;
        }
    </style>
</head>
<body>
<div id="header">
    <div class="title"></div>
    <div class="welcome">
        欢迎您：${user.username}
        您是：<span style="color: red">${user.role==1?'经理':'普通员工'}</span>
        <a href="#" onclick="if(confirm('确定要注销吗？')) { top.location.href='<%= request.getContextPath() %>/logoutServlet'; } return false;" style="text-decoration: none">注销</a>
    <%--        <a href="#" onClick="if(confirm('确定要注销吗？')) { top.location.href='<%= request.getContextPath() %>/logoutServlet'; } return false;"></a>--%>
    </div>
</div>
</body>
</html>
