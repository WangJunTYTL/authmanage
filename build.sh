#!/bin/bash
source /etc/profile
echo $1 
mvn -P$1 clean package -Dmaven.test.skip=true



