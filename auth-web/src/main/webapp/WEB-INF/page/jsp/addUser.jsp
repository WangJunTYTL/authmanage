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
                <h4>用户管理>>添加用户</h4>
                <div>
                    <form action="/admin/addUser.do" method="post">
                        <div class="input-group">
                            <span class="input-group-addon">帐号</span>
                            <input type="text" class="form-control" placeholder="你的账号，建议使用你的英文名字" name="email" required="true" message="用户账号是必须的">
                        </div>

                       <%-- <div class="input-group">
                            <span class="input-group-addon">名字</span>
                            <input type="text" class="form-control" placeholder="真实名字" name="name">
                        </div>--%>

                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" value="1" checked name="isDel">
                        上线

                        <br><br>
                        <h4 class="bs-callout-warning">所属系统</h4>

                        <div class="input-group">
                            <c:forEach items="${requestScope.systems}" var="system">
                                <input type="radio" value="${system.id}" name="systemId" id="system_${system.id}" onclick="getRoles(this)"><label
                                    for="system_${system.id}">${system.name}</label>
                                <%--<label>
                                    <input type="radio" value="${system.id}"> ${system.name}
                                </label>--%>

                            </c:forEach>
                        </div>

                        <h4 class="bs-callout-warning">所属角色</h4>

                        <div class="input-group" id="roleList">
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

<script>
    function getRoles(system) {
        $.getJSON("/admin/" + system.value + "/getRoles.do", function (date) {
            $("#roleList").html("");
            var  roles = date.roles;
            for(var i=0;i<roles.length;i++){
                var id = roles[i].id;
                var name = roles[i].name;
                var newInput = $("<input>").attr("type","checkbox").attr("id","role_"+id).attr("name","roleIds").attr("value",id);
                var newLable= $("<label>"+name+"</label>").attr("for","role_"+id);
                $("#roleList").append(newInput).append(newLable);
            }
        });
    }

    $(function(){
        $("input[id^='system_']").removeAttr("checked");
    });
</script>
</body>
</html>