<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>welcome</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <script src="/js/jquery-1.10.2.js"></script>
</head>
<body>
<script>
    app_data = {
        rules: {
            email: "required",
            passwd: "required"
        },
        messages: {
            email: "邮箱不可以为空",
            passwd: "密码不可以为空"
        },
        url: "/login",
        nextUrl: "/",
        highlight: function (element) {
            $(element).focus();
        },
        errorPlacement: function (error, element) {
            $(element).attr("placeholder", error.html());

        }

    }
</script>
<div class="container" style="margin-top: 25px;">
    <h5 class="page-header">

        <c:choose>
            <c:when test="${!empty sessionScope.current_user}">
                <div>
                    你好，<a href="/me">${sessionScope.current_user}</a> <a class="text-success" href="javascript:void(0)"
                                                                         url="/logout" id="logout">退出</a>
                </div>
            </c:when>
            <c:otherwise>
                <div id="login">
                    <form class="form-inline" role="form" method="post">
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">邮箱</div>
                                <input class="form-control" type="email" placeholder="Enter email"
                                       value="admin@peaceful.com" name="email">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group">
                                <div class="input-group-addon">密码</div>
                                <label class="sr-only" for="exampleInputPassword2">Password</label>
                                <input type="password" class="form-control" id="exampleInputPassword2"
                                       placeholder="Password" name="passwd"
                                       value="10000">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-default">登录</button>
                    </form>
                </div>
            </c:otherwise>
        </c:choose>
    </h5>
    <ol>
        <li><a href="/me">查看我自己在权限中心配置的信息【需要用户登录】</a></li>
        <li><a href="/sys">查看系统在权限中心配置的信息【需要admin角色或拥有getSysInfo功能】</a></li>
        <li><a href="/guest">客人【需要guest角色】</a></li>
    </ol>
</div>
<script src="/js/formvalid/jquery.validate.js"></script>
<script src="/js/formvalid/peaceful.form.js"></script>
<script>
    $(function () {
        $("#logout").click(function () {
            var url = $(this).attr("url");
            $.get(url, function (data) {
                data = JSON.parse(data);
                if (data.code == 1) {
                    alert(data.result);
                    document.location.href = "/";
                }
            });
        });
    });
</script>

</body>
</html>