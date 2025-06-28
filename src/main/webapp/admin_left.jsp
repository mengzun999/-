<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>
<body class="frame-bd">
${user.role}
<c:choose>
    <c:when test="${user.role==1}">
        <ul class="left-menu">
            <li><a href="admin_bill_list.jsp" target="mainFrame"><img src="images/btn_bill.gif" /></a></li>
            <li><a href="providerAdmin" target="mainFrame"><img src="images/btn_suppliers.gif" /></a></li>
            <li><a href="userAdmin" target="mainFrame"><img src="images/btn_users.gif" /></a></li>
            <li><a href="#" onClick="if(confirm('确定要退出系统吗？')) { top.location.href='<%= request.getContextPath() %>/logoutServlet'; } return false;">
                <img src="images/btn_exit.gif" />
                </a>
            </li>
        </ul>
    </c:when>
<c:otherwise>
<ul class="left-menu">
    <li><a href="admin_bill_list.jsp" target="mainFrame"><img src="images/btn_bill.gif" /></a></li>
    <li><a href="#" onClick="if(confirm('确定要退出系统吗？')) { top.location.href='<%= request.getContextPath() %>/logoutServlet'; } return false;">
        <img src="images/btn_exit.gif" />
    </a>
    </li>
    </c:otherwise>
    </c:choose>
</body>
</html>
