<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar navbar-inverse navbar-fixed-top bs-docs-nav" role="banner">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="#" class="navbar-brand">统一权限系统</a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <li>
                    <a href="#getting-started">（内部管理系统，非请勿入）</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:choose>
                    <c:when test="${!empty sessionScope.administrator}">
                        <li>
                            <a href="#about">${sessionScope.administrator.name}</a>
                        </li>
                        <li>
                            <a href="/admin/out.do">退出&nbsp;&nbsp;&nbsp;&nbsp;</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="/login.jsp">请登录&nbsp;&nbsp;&nbsp;&nbsp;</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</header>

