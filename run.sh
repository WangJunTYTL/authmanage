#!/bin/bash
#========================
#create by WangJun
#date 2014-11-06
#email wangjuntytl@163.com
#=============================
#desc build and run app
#==================================

source /etc/profile
ENV=$1
[ "x${ENV}" == "x" ] && ENV='dev' # dev test product
echo '----------------------------------------------'
echo "构建环境：${ENV}"
echo '----------------------------------------------'

echo 'check mvn cmd... '`which mvn`
[ $? != 0 ] && "请先安装maven，并添加mvn到path变量中" && exit 1
echo 'check git cmd... '`which git`
[ $? != 0 ] && "请先安装git，并添加git到path变量中"  && exit 1

#下载依赖包，最好手动将依赖包install到你的本地仓库
[ ! -d "peaceful-basic-platform" ]   && git clone https://github.com/WangJunTYTL/peaceful-basic-platform.git && echo "下载依赖包"

cd peaceful-basic-platform
mvn clean -P${ENV} install -o  -Dmaven.test.skip=true
cd ..

wait
rm -rf peaceful-basic-platform

mvn -P${ENV} clean compile -o -Dmaven.test.skip=true
cd auth-data-protocol
mvn -P${ENV} install -o  -Dmaven.test.skip=true
cd ..

cd auth-service
mvn -P${ENV} install -o  -Dmaven.test.skip=true
cd ..

cd auth-sdk
mvn -P${ENV} install -o  -Dmaven.test.skip=true
cd ..

cd auth-web
mvn jetty:run -o -Dmaven.test.skip=true


