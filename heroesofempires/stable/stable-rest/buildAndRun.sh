#!/bin/sh
mvn clean package && docker build -t hu.javalife/stable-rest .
docker rm -f stable-rest || true && docker run -d -p 9080:9080 -p 9443:9443 --name stable-rest hu.javalife/stable-rest