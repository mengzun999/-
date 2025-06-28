<%@ page import="com.wang.supermarket.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增用户</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            // 页面加载时，默认禁用提交按钮
            $(".btn-success").prop("disabled", true);

            // 表单重置函数
            function resetForm() {
                $("form")[0].reset(); // 重置表单
                $("input[type='text'], input[type='password'], textarea").val(""); // 清空文本框
                $("input[type='radio'], input[type='checkbox']").prop("checked", false); // 取消选中单选/复选框
                $("select").prop("selectedIndex", 0); // 重置下拉框为第一个选项

                // 清除提示信息
                $("span").html("");

                // 禁用提交按钮
                $(".btn-success").prop("disabled", true);
            }

            // 绑定重置按钮点击事件
            $("[type='button']").on("click", function () {
                resetForm();
            });

            // 验证用户名
            $("[name='username']").blur(function () {
                let name = $(this).val();
                if (name.length === 0) {
                    $("#sp_username").html("<font color='red'>用户名称不能为空！</font>");
                    $("#sp_username").fadeIn(2000).fadeOut(2000);
                    $(".btn-success").prop("disabled", true);
                } else {
                    $.getJSON(
                        "${pageContext.request.contextPath}/hasUsername",
                        { username: name },
                        function (obj) {
                            if (obj) {
                                $("#sp_username").html("<font color='red'>对不起，该用户名已经被注册！</font>");
                                $("#sp_username").fadeIn(2000).fadeOut(2000);
                                // $(".btn-success").prop("disabled", true);
                            } else {
                                $("#sp_username").html("<font color='green'>Ok！</font>");
                                $("#sp_username").fadeIn(2000).fadeOut(2000);
                                // $(".btn-success").prop("disabled", false);
                            }
                        }
                    );
                }
            });

            // 验证密码是否合规
            $("[name='password']").blur(function () {
                let $pwd = $(this).val();
                if ($pwd.length === 0) {
                    $("#sp_password").html("<font color>密码不能为空</font>");
                    return;
                } else {
                    $.getJSON(
                        "${pageContext.request.contextPath}/chkPwd",
                        { password: $pwd },
                        function (obj) {
                            if (obj) {
                                $("#sp_password").html("<font color='green'>Ok！</font>");
                                $(".btn-success").prop("disabled", false);
                            } else {
                                $("#sp_password").html("<font color='red'>密码必须是6-10位之间</font>");
                                $(".btn-success").prop("disabled", true);
                            }
                        }
                    );
                }
            });

            // 新增：验证确认密码是否与主密码一致
            $("[name='reppwd']").on("input", function () {
                let pwd = $("[name='password']").val();
                let repPwd = $(this).val();

                if (repPwd === "") {
                    $("#sp_reppwd").html(""); // 确认密码为空时不提示
                } else if (pwd !== repPwd) {
                    $("#sp_reppwd").html("<font color='red'>两次输入的密码不一致！</font>");
                    $(".btn-success").prop("disabled", true); // 禁用提交按钮
                } else {
                    $("#sp_reppwd").html("<font color='green'>密码一致</font>"); // 可选：显示绿色 OK 提示
                }
            });

            // 当主密码变化时触发确认密码检查
            $("[name='password']").on("input", function () {
                $("[name='reppwd']").trigger("input");
            });

            // 表单整体验证逻辑
            function validateForm() {
                let isValid = true;

                // 检查所有文本、密码和下拉框是否填写完整
                $("input[type='text'], input[type='password'], select").each(function () {
                    if ($(this).val() === null || $(this).val().trim() === "") {
                        isValid = false;
                        return false;
                    }
                });

                // 检查是否有选中的性别
                if ($("input[name='sex']:checked").length === 0) {
                    isValid = false;
                }

                // 新增验证：检查是否有选中的用户角色
                if ($("input[name='role']:checked").length === 0) {
                    isValid = false;
                }

                // 额外验证：密码是否一致
                const pwd = $("[name='password']").val();
                const reppwd = $("[name='reppwd']").val();
                if (pwd !== reppwd && reppwd !== "") {
                    isValid = false;
                }

                return isValid;
            }


            // 监听输入框变化以更新提交按钮状态
            $("input[type='text'], input[type='password'], input[type='radio'], select").on("input change", function () {
                if (validateForm()) {
                    $(".btn-success").prop("disabled", false);
                } else {
                    $(".btn-success").prop("disabled", true);
                }
            });

            if (!validateForm()) {
                $(".btn-success").prop("disabled", true);
            }
        });
    </script>

<%--    <script type="text/javascript">--%>
<%--        $(function () {--%>
<%--            //表单提交的验证--%>
<%--            // $("form").submit(function (){--%>
<%--            //     //判断是否选择了性别：只需要判断有选择即可--%>
<%--            //     let $sex = $("[name='sex']:checked");--%>
<%--            //     if($sex.length === 0){--%>
<%--            //         $("#sp_sex").html("<font color='red'>请选择性别</font>");--%>
<%--            //         return false;--%>
<%--            //     }else{--%>
<%--            //         $("#sp_sex").html("<font color='green'>OK</font>");--%>
<%--            //         return  true;--%>
<%--            //     }--%>
<%--            // })--%>
<%--            //隐藏span--%>
<%--            $("#sp_username").hide();--%>
<%--            //验证用户名--%>
<%--            $("[name='username']").blur(function (){--%>
<%--                let name=$(this).val();--%>
<%--                if(name.length === 0){--%>
<%--                    $("#sp_username").html("<font color='red'>用户名称不能为空！</font>")--%>
<%--                    $("#sp_username").fadeIn(2000).fadeOut(2000);--%>
<%--                    //禁用提交--%>
<%--                    $(".btn-success").prop("disabled",true);--%>
<%--                }else{--%>
<%--                    $.getJSON( //AJAX--%>
<%--                        "${pageContext.request.contextPath}/hasUsername",--%>
<%--                        {username:name},--%>
<%--                        function (obj){--%>
<%--                            if(obj){--%>
<%--                                $("#sp_username").html("<font color='red'>对不起，该用户名已经被注册！</font>")--%>
<%--                                $("#sp_username").fadeIn(2000).fadeOut(2000);--%>
<%--                                $(".btn-success").prop("disabled",true);--%>
<%--                            }else {--%>
<%--                                $("#sp_username").html("<font color='green'>Ok！</font>")--%>
<%--                                $("#sp_username").fadeIn(2000).fadeOut(2000);--%>
<%--                                $(".btn-success").prop("disabled",false);--%>
<%--                            }--%>
<%--                        }--%>
<%--                    )--%>
<%--                }--%>
<%--            });--%>
<%--            //验证密码是否合规--%>
<%--            $("[name='password']").blur(function (){--%>
<%--                let $pwd = $(this).val();--%>
<%--                //判断是否为空--%>
<%--                if($pwd.length === 0){--%>
<%--                    $("#sp_password").html("<font color>密码不能为空</font>");--%>
<%--                    return;--%>
<%--                }else {--%>
<%--                    //判断规则--%>
<%--                    $.getJSON(--%>
<%--                        "${pageContext.request.contextPath}/chkPwd",--%>
<%--                        {password:$pwd},--%>
<%--                        function (obj){--%>
<%--                            if(obj){--%>
<%--                                $("#sp_password").html("<font color='green'>Ok！</font>")--%>
<%--                                $(".btn-success").prop("disabled",false);--%>
<%--                            }else{--%>
<%--                                $("#sp_password").html("<font color='red'>密码必须是6-10位之间</font>")--%>
<%--                                $(".btn-success").prop("disabled",true);--%>
<%--                            }--%>
<%--                        }--%>
<%--                    );--%>
<%--                }--%>
<%--            });--%>
<%--            //验证手机号--%>
<%--            $("[name='mobile']").blur(function (){--%>
<%--                let $m = $(this).val();--%>
<%--                //判断是否为空--%>
<%--                if($m.length === 0){--%>
<%--                    $("#sp_mobile").html("<font color>手机号不能为空</font>");--%>
<%--                    return;--%>
<%--                }else {--%>
<%--                    //判断规则--%>
<%--                    $.getJSON(--%>
<%--                        "${pageContext.request.contextPath}/chkMobile",--%>
<%--                        {mobile:$m},--%>
<%--                        function (obj){--%>
<%--                            if(obj){--%>
<%--                                $("#sp_mobile").html("<font color='green'>Ok！</font>")--%>
<%--                                $(".btn-success").prop("disabled",false);--%>
<%--                            }else{--%>
<%--                                $("#sp_mobile").html("<font color='red'>手机号码格式错误！</font>")--%>
<%--                                $(".btn-success").prop("disabled",true);--%>
<%--                            }--%>
<%--                        }--%>
<%--                    );--%>
<%--                }--%>
<%--            })--%>
<%--            //验证年龄--%>
<%--            $("[name='age']").blur(function (){--%>
<%--                let $m = $(this).val();--%>
<%--                //判断是否为空--%>
<%--                if($m.length === 0){--%>
<%--                    $("#sp_age").html("<font color>年龄不能为空</font>");--%>
<%--                    return;--%>
<%--                }else {--%>
<%--                    //判断规则--%>
<%--                    $.getJSON(--%>
<%--                        "${pageContext.request.contextPath}/chkAge",--%>
<%--                        {age:$m},--%>
<%--                        function (obj){--%>
<%--                            if(obj){--%>
<%--                                $("#sp_age").html("<font color='green'>Ok！</font>")--%>
<%--                                $(".btn-success").prop("disabled",false);--%>
<%--                            }else{--%>
<%--                                $("#sp_age").html("<font color='red'>年龄格式错误！</font>")--%>
<%--                                $(".btn-success").prop("disabled",true);--%>
<%--                            }--%>
<%--                        }--%>
<%--                    );--%>
<%--                }--%>
<%--            });--%>
<%--        })--%>
<%--    </script>--%>
    <%
        User user = (User) request.getAttribute("user");
    %>

</head>
<body>
    <div class="container">
    <form action="${pageContext.request.contextPath}/addUser" method="post">
        用户名称：<input type="text" name="username" value="<%= user != null ? user.getUsername() : "" %>">
        <span id="sp_username"></span>
        <br>
        用户密码：<input type="password" name="password" value="<%= user != null ? user.getPassword() : "" %>">
        <span id="sp_password"></span><br>
        确认密码：<input type="password" name="reppwd" value="<%= user != null ? user.getPassword() : "" %>">
        <span id="sp_reppwd"></span><br>
        用户性别：<input type="radio" name="sex" value="0" <%= user != null && user.getSex() == 0 ? "checked" : "" %>>男
        <input type="radio" name="sex" value="1" <%= user != null && user.getSex() == 1 ? "checked" : "" %>>女
        <span id="sp_sex"></span><br>
        用户年龄：<input type="text" name="age" value="<%= user != null ? user.getAge() : "" %>">
        <span id="sp_age"></span><br>
        用户地址：<input type="text" name="address" value="<%= user != null ? user.getAddress() : "" %>"><br>
        用户手机：<input type="text" name="mobile" value="<%= user != null ? user.getMobile() : "" %>">
        <span id="sp_mobile"></span><br>
        <input type="hidden" name="id" value="<%= user != null ? user.getId() : "" %>">
        用户角色：<input type="radio" name="role" value="0" <%= user != null && user.getRole() == 0 ? "checked" : "" %>>普通用户
        <input type="radio" name="role" value="1" <%= user != null && user.getRole() == 1 ? "checked" : "" %>>经理<br>
        <button class="btn btn-success" type="submit">提交</button>
        <button class="btn btn-primary" type="button" onclick="resetForm()">重置</button>
    </form>
    </div>
</body>
</html>
