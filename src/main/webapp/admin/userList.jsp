<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>用户列表</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script>
        $(function (){
            //禁用
            $(".btn-warning").click(function (){
                alert($(this).val());
                $.getJSON(
                    "updateUser",
                    {id:$(this).val()},
                    function (obj){
                        if(obj){
                            alert("修改成功！");
                            location.reload();//刷新页面
                        }else{
                            alert("对不起，操作失败！");
                        }
                    }
                )
            })

            $(".btn-danger").click(function (){
                alert($(this).val());
                $.getJSON(
                    "updateUser",
                    {id:$(this).val()},
                    function (obj){
                        if(obj){
                            alert("修改成功！");
                            location.reload();//刷新页面
                        }else{
                            alert("对不起，操作失败！");
                        }
                    }
                )
            })
        })
    </script>
    <style>
        th{
            text-align: center;
        }
        td{
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <table class="table-bordered table-hover table">
        <tr>
            <th>用户名称</th>
            <th>用户密码</th>
            <th>用户性别</th>
            <th>用户手机</th>
            <th>用户年龄</th>
            <th>用户地址</th>
            <th>用户状态</th>
            <th>
                <button class="btn btn-success" onclick="javascript:location='admin/add.jsp'">新增用户</button>
            </th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>
                <td>${user.sex==0?"男":"女"}</td>
                <td>${user.mobile}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.is_del==0?"活动":"禁用"}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.is_del==0}">
                            <button class="btn btn-warning" value="${user.id}">禁用</button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-danger" value="${user.id}">启用</button>
                        </c:otherwise>
                    </c:choose>
                    <button class="btn btn-primary" onclick="location.href='${pageContext.request.contextPath}/toUpdate?id=${user.id}'">修改</button>
                </td>
            </tr>
            </c:forEach>
    </table>
</div>
</body>
</html>