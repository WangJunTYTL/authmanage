#!/bin/bash
#========================
# create by WangJun
# date 2014-11-06
# email wangjuntytl@163.com
#
# =============================
#
# 描述：构建脚本
#
# 如果你的开发平台是window，需要手动执行以下步骤
#   1. git clone https://github.com/WangJunTYTL/peaceful-basic-platform.git
#   2. 进入clone 目录 ，执行 mvn install  -Dmaven.test.skip=true
#   3. 跳出进入authmanage 目录，执行 mvn install  -Dmaven.test.skip=true
#   4. 切入到auth-web 执行 mvn jetty:run [注意请先配置你的数据库]
#   5，访问 127.0.0.1:8888
#==================================

source /etc/profile
ENV=$1
[ "x${ENV}" == "x" ] && ENV='dev' # dev test product
echo '----------------------------------------------'
echo "构建环境：${ENV}"
echo '----------------------------------------------'
echo "构建工具git、mvn 安装检测"

cmd_is_exist(){
    echo "check $1 cmd is install ... "
    _r=`which $1`
    if [ $? == 0 ];then
        echo "OK"
    else
        echo "请先安装$1，并添加$1到PATH变量中" && exit 1
    fi
}

cmd_is_exist "mvn"
cmd_is_exist "git"
echo '----------------------------------------------'

wait
echo "准备下载依赖包并开始构建 ..."

#下载依赖包，最好手动将依赖包install到你的本地仓库
if [ ! -d "./peaceful-basic-platform" ];then
   git clone https://github.com/WangJunTYTL/peaceful-basic-platform.git ||  exit 1
else
   echo '依赖包已经存在了...'
fi

cd peaceful-basic-platform
cd peaceful-parent
mvn clean -P${ENV} install  -Dmaven.test.skip=true || exit 1
cd ..

cd peaceful-common-utils
mvn clean -P${ENV} install  -Dmaven.test.skip=true || exit 1
cd ..
wait
cd ..

wait
rm -rf peaceful-basic-platform

mvn -f pom.xml -P${ENV} install -Dmaven.test.skip=true || exit 1

cd auth-web
mvn jetty:run -o -Dmaven.test.skip=true


