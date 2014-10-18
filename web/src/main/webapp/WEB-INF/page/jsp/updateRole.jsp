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

    <link rel="stylesheet" type="text/css" href="/css/zTreeStyle.css">
    <script type="text/javascript" src="/js/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="/js/jquery.ztree.excheck-3.5.js"></script>


</head>
<body>
<jsp:include page="/resources/pages/header.jsp"></jsp:include>
<div class="bs-docs-container container">
    <div class="row">
        <jsp:include page="/resources/pages/menus.jsp"></jsp:include>
        <jsp:include page="/resources/pages/modal.jsp"></jsp:include>
        <div class="col-sm-10" role="main">
            <div class="bs-callout bs-callout-warning" id="jquery-required" style="margin-top: 30px;">
                <h4>角色管理>>修改角色</h4>
                <br>

                <div>
                    <form action="/admin/updateRole.do" method="post">

                        <div class="input-group">
                            <span class="input-group-addon">名字</span>
                            <input type="text" class="form-control" placeholder="角色名字" name="name" value="${role.name}"
                                   required="true" message="角色名字是必须的">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="id" value="${role.id}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="systemId"
                                   value="${role.system.id}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="createTime"
                                   value="${role.createTime}">
                            <input type="hidden" class="form-control" placeholder="角色名字" name="operator"
                                   value="${role.operator}">
                        </div>


                        <div class="input-group">
                            <span class="input-group-addon">描述</span>
                            <input type="text" class="form-control" placeholder="简单描述下这个角色" name="description"
                                   value="${role.description}">
                        </div>
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <c:choose>
                            <c:when test="${empty role}">
                                <input type="checkbox"
                                       value="1"
                                       checked name="isdel"> 上线
                            </c:when>
                            <c:when test="${role.isdel == 1}">
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
                        <br>
                        <br>
                        <h4 class="bs-callout-warning">拥有的菜单</h4>

                        <div class="input-group" id="groupList">
                            <input id="funIds" type="hidden" name="menuIds"/>

                            <div class="auth-item fun-item">
                                <ul class="item-box ztree"></ul>
                            </div>
                        </div>

                        <div class="input-group">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <button type="button" class="btn btn-default" id="mysubmit">保存</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/my.js"></script>
<script>
    $(
            function () {
                $.getJSON("/admin/" + ${role.system.id} +"/getMenus.do", function (date) {
                    var menus = date.menus;
                    var treeData = [];
                    for (var i = 0; i < menus.length; i++) {
                        var data = {};
                        data.id = menus[i].id;
                        if (menus[i].parentId == null)
                            data.pId = 0;
                        else
                            data.pId = menus[i].parentId;
                        data.name = menus[i].name;
                        <c:forEach items="${role.menus}" var="menu">
                        if (menus[i].id == ${menu.id}) {
                            data.checked = true;
                        }
                        </c:forEach>
                        if (menus[i].isdel == 0)
                            data.chkDisabled = true;
                        data.open = true;
                        treeData.push(data);
                    }

//                    alert(JSON.stringify(treeData));
                    var setting = {
                        view: {
                            showIcon: false
                        },
                        check: {
                            chkStyle: "checkbox",
                            radioType: "all",
                            enable: true
                        },
                        data: {
                            simpleData: {
                                enable: true
                            }
                        },
                        callback: {
                            onCheck: zTreeOnCheck
                        }
                    };

                    var tree = $.fn.zTree.init($(".fun-item .item-box"), setting, treeData);

                    function zTreeOnCheck() {
                        var funIds = $('#funIds');
                        var ids = [];
                        var selecteds = tree.getCheckedNodes(true);
                        $.each(selecteds, function () {
                            ids.push(this.id);
                        })
                        funIds.val(ids.join(','));
                    };


                    zTreeOnCheck();
                });
            })
    ;
</script>
</body>
</html>