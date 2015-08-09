<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-2">
    <div class="bs-sidebar hidden-print affix" role="complementary">
        <ul class="nav bs-sidenav">

            <li class="active">
                <a href="#">系统管理</a>
                <ul class="nav">
                    <li id="navA"><a href="/admin/addSystemPre.do">添加系统</a>
                    </li>
                    <li id="navB"><a
                            href="/admin/findSystem.do">查询系统</a></li>

                </ul>
            </li>
            <li class="active">
                <a href="#">角色管理</a>
                <ul class="nav">
                    <li id="navC"><a href="/admin/addRolePre.do">添加角色</a>
                    </li>
                    <li id="navD"><a
                            href="/admin/findRoles.do">查询角色</a></li>

                </ul>
            </li>

            <li class="active">
                <a href="#">功能管理</a>
                <ul class="nav">
                    <li id="navE"><a href="/admin/add/function/pre.do">添加功能</a>
                    </li>
                    <li id="navF"><a
                            href="/admin/find/functions.do">查询功能</a></li>

                </ul>
            </li>

            <li class="active">
                <a href="#">粒子管理</a>
                <ul class="nav">
                    <li id="navG"><a href="/admin/add/bean/pre.do">添加粒子</a>
                    </li>
                    <li id="navH"><a
                            href="/admin/find/beans.do">查询粒子</a></li>

                </ul>
            </li>

            <li class="active">
                <a href="#">资源管理</a>
                <ul class="nav">
                    <li id="navI"><a href="/admin/addResourcePre.do">添加资源</a>
                    </li>
                    <li id="navJ"><a
                            href="/admin/findResources.do">查询资源</a></li>

                </ul>
            </li>
            <li class="active">
                <a href="#">用户管理</a>
                <ul class="nav">
                    <li id="navK"><a href="/admin/addUserPre.do">添加用户</a>
                    </li>
                    <li id="navL"><a
                            href="/admin/findUsers.do">查询用户</a></li>

                </ul>
            </li>


        </ul>
    </div>
</div>

<script>
    function addCookie(objName, objValue, objHours) {//添加cookie
        var str = objName + "=" + escape(objValue);
        if (objHours > 0) {//为0时不设定过期时间，浏览器关闭时cookie自动消失
            var date = new Date();
            var ms = objHours * 3600 * 1000;
            date.setTime(date.getTime() + ms);
            str += "; expires=" + date.toGMTString()+"; path=/";
        }
        document.cookie = str;
    }

    function getCookie(objName) {//获取指定名称的cookie的值
        var arrStr = document.cookie.split("; ");
//        alert(arrStr)
        for (var i = 0; i < arrStr.length; i++) {
            var temp = arrStr[i].split("=");
            if (temp[0] == objName) return unescape(temp[1]);
        }
    }

    function delCookie(name) {//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
        var date = new Date();
        date.setTime(date.getTime() - 10000);
        document.cookie = name + "=a; expires=" + date.toGMTString()+"; path=/";
    }

    $(function(){
        $("li[id^='nav']").click(function(){
            delCookie("nav")
            addCookie("nav",this.id,5);
        });
        var id = getCookie("nav");
//        alert(id);
        $("#"+id).attr("class","active");
    });
</script>
