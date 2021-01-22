#!/bin/sh
if [ $(docker ps -a -f name=dmit2015-1202-oa01-instructor-demos | grep -w dmit2015-1202-oa01-instructor-demos | wc -l) -eq 1 ]; then
  docker rm -f dmit2015-1202-oa01-instructor-demos
fi
mvn clean package && docker build -t org.example/dmit2015-1202-oa01-instructor-demos .
docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-1202-oa01-instructor-demos org.example/dmit2015-1202-oa01-instructor-demos
