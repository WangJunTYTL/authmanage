<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="WangJun">

    <title>
        登录
    </title>

    <link href="css/bootstrap3.css" rel="stylesheet">
    <link href="css/docs.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">


</head>
<body>
<jsp:include page="resources/pages/header.jsp"></jsp:include>
<div class="container">
    <div style="margin-top: 150px;">
        <div style="width: 400px;margin: 0 auto;">
            <form class="form-horizontal" role="form" action="/login.do" method="post">
                <div class="form-group">
                    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>

                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="inputEmail3" placeholder="Email" name="email" required="true" message="email是必须的">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>

                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="password" required="true" message="password是必须的">
                        <div class="text-danger">${requestScope.msg}</div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-default" id="normalSubmit">登录</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/my.js"></script>
</body>
</html>