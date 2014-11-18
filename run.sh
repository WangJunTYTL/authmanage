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
echo ${ENV}

[ ! -d "peaceful-common-utils" ] && git clone git@github.com:WangJunTYTL/peaceful-parent.git
[ ! -d "peaceful-common-utils" ] && git clone git@github.com:WangJunTYTL/peaceful-common-utils.git


mvn -P${ENV} clean compile -Dmaven.test.skip=true

cd peaceful-parent
mvn -P${ENV} install -o  -Dmaven.test.skip=true
cd ..


cd peaceful-common-utils
mvn -P${ENV} install -o  -Dmaven.test.skip=true
cd ..

rm -rf peaceful-parent
rm -rf peaceful-common-utils


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
mvn jetty:run -Dmaven.test.skip=true


