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
        手册
    </title>
    <link rel="shortcut icon" type="image/vnd.microsoft.icon" href='/image/icon.png'>
    <link href="/css/bootstrap3.css" rel="stylesheet">
    <link href="/css/docs.css" rel="stylesheet">
    <link href="/css/common.css" rel="stylesheet">

    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.js"></script>
</head>
<body>
<div class="bs-docs-container container">
    <div class="bs-callout bs-callout-warning" id="jquery-required" style="margin-top: -30px;">
        <h4 class="page-header">关于</h4><br>

        <p>权限中心系统是由大街基础平台部开发的一套统一控制系统权限的API，它的基本功能如下：</p>

        <p class="text-center">
            <img src="/image/authDesign.jpg">
        </p>
        <h4 class="page-header" id="config">使用与配置</h4><br>

        <p>
            一、首先你需要权限中心去注册你的系统，如下,你可以在里面填下自己系统一些的基本信息，系统会为你的系统分配一个唯一标识，<br>当你配置你的系统是会用到这个标识
        </p>

        <p class="text-center">
            <img src="/image/registSystem.jpg" width="876">
        </p>
        二、引入API包，并在你项目的classpath根路径引入配置文件：auth.properties
        <p class="text-center">

            <code>
                <pre>
                1.0 authApi mvn坐标
                                  groupId:com.dajie
                                  artifactId:authApi
                                  version:1.0-SNAPSHOT
                2.0 authApi mvn坐标发生改变
                                  groupId:com.dajie.nuggets
                                  artifactId:authApi
                                  version:2.0-SNAPSHOT
                1.0 auth.properties配置
                                  auth.systemId=16    // 系统标识，当在系统中心添加系统会分配一个唯一的系统标识<br>
                                  auth.AuthServiceAddress=http://192.168.27.57:8412   //权限服务中心地址<br>
                                  auth.sessionOutTime=2 //权限中心建立会话超时时间，与权限中心建立一次会话的过程，会把数据缓存在你的系统当中，
                                        过了这个时间，缓存数据也就消失，建议该值最好和你的系统会话时间一致，
                                        这样会提高你的系统性能，（单位秒）
                2.0 auth.properties文件配置发生改变
                                  auth.app.id=1  //系统标识
                                  auth.service.address=http://192.168.14.205:8080 //权限中心地址
                                  auth.user.session.out.time=2 // 用户信息缓存时间
                                  auth.system.session.out.time=300 // 系统信息缓存时间
                获得权限服务唯一调用入口(注意会抛出一个让你明确处理的异常):
                                  AuthService authService = AuthServiceImpl.getAuthService();
                    </pre>
            </code>
        </p>
        <p>
            三、配置已经完成了，你可以在权限中心系统配置你的基本权限设置，然后通过提供的API获得你想要的数据：例如加入权限
        </p>

        <p class="text-center">
            <img src="/image/authConfig.jpg" width="876">
        </p>
        <br><br>
        <h4 class="page-header" id="createSessionException">关于create session exception</h4><br>

        <p>
            <pre>
          出现此异常一般会有三种可能:<br>
                一、权限系统服务正在维护中，如果是这种原因，你可以直接通过浏览器直接访问下权限中心系统确认服务是否正在正常服务
                二、未配置auth.properties或配置格式有问题或引入路径有问题
                三、未在权限中心系统注册你的系统或配置的服务地址错误
        </pre>
        </p>
        <br><br>
        <h4 class="page-header" id="update">关于更新</h4><br>

        <p>
            <pre>
          发布2.0版本authApi<br>
                2.0版本authApi, 增加可选的分组功能,可以对菜单,角色,资源进行分组,(角色,资源分组功能未开放,但系统已支持)
                1.0版本向后兼容,1.0版本支持升级后权限中心会话,但1.0版本没有2.0版本功能强大,建议使用1.0版本的升级到2.0
                2.0mvn坐标发生改变: groupId:com.dajie.nuggets
                                  artifactId:authApi
                                  version:2.0-SNAPSHOT
                2.0 auth.properties文件配置发生改变
                                  auth.app.id=1  //系统标识
                                  auth.service.address=http://192.168.14.205:8080 //权限中心地址
                                  auth.user.session.out.time=2 // 用户信息缓存时间
                                  auth.system.session.out.time=300 // 系统信息缓存时间
        </pre>
        </p>
    </div>
</div>
</body>
</html>