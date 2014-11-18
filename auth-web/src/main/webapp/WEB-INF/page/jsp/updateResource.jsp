<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                <h4>资源管理>>修改资源</h4>
                <br>

                <div>
                    <form action="/admin/updateResource.do" method="post">
                        <div class="input-group">
                            <span class="input-group-addon">模式</span>
                            <input type="text" class="form-control" placeholder="资源匹配模式" name="pattern" value="${resource.pattern}" required="true" message="模式是必须的">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="id" value="${resource.id}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="systemId"
                                   value="${resource.system.id}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="createTime"
                                   value="${resource.createTime}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="operator"
                                   value="${resource.operator}">
                        </div>


                        <div class="input-group">
                            <span class="input-group-addon">描述</span>
                            <input type="text" class="form-control" placeholder="简单描述下这个资源模式" name="description" value="${resource.description}">
                        </div>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <c:choose>
                            <c:when test="${empty resource}">
                                <input type="checkbox"
                                       value="1"
                                       checked name="isdel"> 上线
                            </c:when>
                            <c:when test="${resource.isdel == 1}">
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

                        <br><br>
                        <h4 class="bs-callout-warning">所属角色</h4>

                        <div class="input-group" id="roleList">
                            <c:forEach items="${system.roles}" var="role">
                                <c:set var="iscontain" value="false"/>
                                <c:forEach var="resourceRole" items="${resource.roles}">
                                    <c:if test="${resourceRole eq role}">
                                        <c:set var="iscontain" value="true"/>
                                    </c:if>
                                </c:forEach>
                                <c:choose>
                                    <c:when test="${iscontain}">
                                        <input checked name="roleIds" value="${role.id}" type="checkbox">${role.name}
                                    </c:when>
                                    <c:otherwise>
                                        <input  name="roleIds" value="${role.id}" type="checkbox">${role.name}
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
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