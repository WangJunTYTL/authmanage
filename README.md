auth manage
===============

这是一个统一管理系统权限，设计的目标是给开发者一个完整地权限管理系统，以便你能更专注自己系统业务的开发

## 更新

*请注意：2015/08/08 之后重写了服务中心的核心业务逻辑，与之前的版本已不在兼容，之前的版本称为1.0，老版本已在master分支打了tag 1.0 ，本次改动后的版本命名为2.0*

*新增功能：*

*1. sdk与服务中心通信时，增加了安全认证，所以sdk的auth.properties配置文件和1.0的相比有变化*

*2. 服务中心新增因子管理的概念，用于更细粒度的控制，用以控制方法内部的数据展示，弥补之前粒度之可以控制到方法的访问*

*3. 菜单的概念命名放弃，重命名为功能*


## 特点

1. 统一配置界面
1. 简洁的客户端SDK包
1. 支持不同平台，不同语言
1. 易扩展
1. 粒度随意掌控，可大可小
1. 支持spring

## 项目文档

* [项目介绍](http://wangjuntytl.github.io/project.html)
* [api](http://wangjuntytl.github.io/apidocs/)

## 安装与运行

1. 运行之前请先安装maven和git
2. 将代码clone到本地后，需要先修改./auth-web/src/man/filters/dev.properties,配置你自己的数据库连接
2. 执行sh run.sh，成功启动后访问 [http://localhost:8888](http://localhost:8888)，如果看到登陆页面，说明程序启动成功
1. 关于登陆账号，请继续向下看数据库创建

## 数据库创建

当程序第一次成功启动后，会自动在你配置的数据库中创建数据表，你需要administrator表添加一对账号和密码
作为你当前的登陆账号，现在一个完整地权限中心已经运行起来了，接下来请看客户端SDK的介绍

## 客户端开发

##### 加入依赖：
    <groupId>com.peaceful</groupId>
    <artifactId>nuggets-auth-sdk</artifactId>
    <version>1.0-SNAPSHOT</version>

##### auth.properties 配置
    #客户端在服务中心注册id
    #当客户端引入sdk包时，需要现在权限中心->添加系统，注册服务，成功后会为该客户端生成一个唯一id，标识你的系统；
    auth.app.id=1
    #权限中心地址
    #sdk包与权限中心建立连接，需要知道权限中心的位置
    auth.service.address=http://10.10.1.110:8080
    #user info 缓存时间
    #第一次通过getUser()获取用户权限信息时，会把配置信息缓存在客户端的服务器，之后会在缓存中获取
    #如果缓存失效，会再次请求权限中心
    auth.user.session.out.time=2
    #system info 缓存时间 ，目的同上
    auth.system.session.out.time=300
    #实现AuthContext抽象类的路径
    #这个是可选配置，只有当你引入auth-spring包时才会使用到这个配置，
    #当服务启动时会实例化指定包路径类的对象，然后sdk将通过AuthContext实例拿取一些必要的上下文信息,比如每次请求用户名是什么
    auth.context.impl.class=xxx


##### 获得服务
    AuthService authService = AuthServiceImpl.getAuthService();
    #这是通过sdk包与权限中心建立服务的唯一入口，当启动后，sdk会读取auth.properties去服务中心校验是否注册服务
    #如果存在注册信息将返回一个可用的服务实例,否则将会抛出未在权限中心注册服务异常

##### 两个主要服务接口

*getSystem()*  获得客户端在权限中心注册的所有配置信息

*getUser(String email)*  获得客指定用户的所有配置信息

有了这两个接口你就可以很方便的进行扩展，因为通过这两个接口你拿到的基本上是所有配置信息，此外，不要担心性能问题，因为通过这两个接口拿到的
数据会缓存在客户端的服务器，你也没必要担心缓存数据过时问题，因为客户端会实时监测服务中心数据配置的变化，若有变化，客户端会立即重新加载缓存

## 扩展

当前sdk的包只发行了java版本，该版本除了依赖Ehcache包为了实现客户端的缓存外，不依赖任何第三方包，所以只要
你当前的开发语言可以和java语言相互调用就可适用于用你的系统，比如当前我们正把sdk包用与spring编写
的项目和scala编写的项目当中，实际运行中表现非常稳定。但由于各个客户端的或者各个公司平台的差异性，
可能需要你作出一些必要的扩展，目的是保证sdk包可以适用于更多的架构同时也保证你们在基于sdk包进行开发时
可以简单方便的去扩展.当前提供的auth-spring包只是一个扩展的列子，它是适用于spring mvc架构的项目，扩展主要
内容是客户端可以通过springmvc HandlerInterceptorAdapter 拦截用户请求，然后通过检查controller上的注解
决定动作的执行方向，另外，还针对视图是jsp页面的写了一个jstl文件，当页面通过jstl拿取菜单时，会先通过authContext(
这需要客户端自己实现authContext接口)拿到当前用户身份，根据用户身份渲染不同的菜单


### 客户端支持jstl表达式

下面是一个样例，会根据客户端系统的当前登录是否在页面渲染functionKey=goods的按钮，按钮的样式是bootstrap3.3版本定义，这需要根据你自己的系统
具体定制，具体样例参照类 MenuUtils

    <%@taglib uri="http://com.peacuful.com/auth/menu" prefix="menu" %>
    <menu:menu functionKey="goods" menuLevel="L1"/>

### 客户端支持注解配置

下面是一个样例，会根据客户端系统的当前登录用户决定是否可以请求addMeta controller

    @AUTH.Function({"goods,mall"})
    @AUTH.Role("admin")
    @RequestMapping(value = "/item/xxx", method = {RequestMethod.POST})
    public void addMeta(HttpServletRequest request, HttpServletResponse response) {

        ...

    }


### 开发样例

可参考demo项目 example




















