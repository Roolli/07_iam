@echo off
call mvn clean package
call docker build -t hu.javalife/stable-rest .
call docker rm -f stable-rest
call docker run -d -p 9080:9080 -p 9443:9443 --name stable-rest hu.javalife/stable-rest