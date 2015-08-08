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
        权限管理
    </title>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href='/image/icon.png'>

    <link href="/css/bootstrap3.css" rel="stylesheet">
    <link href="/css/docs.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.js"></script>
</head>
<body>
<jsp:include page="/resources/pages/header.jsp"></jsp:include>
<div class="bs-docs-container container">
    <div class="row">
        <jsp:include page="/resources/pages/menus.jsp"></jsp:include>
        <jsp:include page="/resources/pages/modal.jsp"></jsp:include>

        <div class="col-sm-10" role="main">
            <div class="bs-callout bs-callout-warning" id="jquery-required" style="margin-top: 30px;">
                <h4>系统管理>>修改系统</h4>

                <div>
                    <form action="/admin/updateSystem.do" method="post">

                        <div class="input-group">
                            <span class="input-group-addon">名字</span>
                            <input type="text" class="form-control" placeholder="系统名字" name="name" value="${system.name}" required="true" message="系统名字是必须的">
                            <input type="hidden" class="form-control" placeholder="系统名字" name="id" value="${system.id}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="createTime" value="${role.createTime}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="operator" value="${role.operator}">
                        </div>

                        <div class="input-group">
                            <span class="input-group-addon">首页</span>
                            <input type="text" class="form-control" placeholder="系统的首页" name="webIndex" value="${system.webIndex}">
                        </div>

                        <div class="input-group">
                            <span class="input-group-addon">描述</span>
                            <input type="text" class="form-control" placeholder="简单描述下这个系统" name="description" value="${system.description}">
                        </div>

                        <div class="input-group">
                            <span class="input-group-addon">标识</span>
                            <input type="text" class="form-control" placeholder="SDK与服务中心通信key，系统会自动生成" name="appkey" value="${system.appkey}" readonly>
                        </div>

                        <div class="input-group">
                            <span class="input-group-addon">秘钥</span>
                            <input type="text" class="form-control" placeholder="SDK与服务中心通信秘钥，系统会自动生成" name="secret" value="${system.secret}" readonly>
                        </div>

                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <c:choose>
                            <c:when test="${empty system}">
                                <input type="checkbox"
                                       value="1"
                                       checked name="isdel"> 上线
                            </c:when>
                            <c:when test="${system.isdel == 1}">
                                <input type="checkbox"
                                       value="1"
                                       checked name="isdel"> 上线
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox"
                                       value="1"
                                        name="isdel"> 上线
                            </c:otherwise>
                        </c:choose>


                        <div class="input-group">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-default" id="mysubmit">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/my.js"></script>
</body>
</html>