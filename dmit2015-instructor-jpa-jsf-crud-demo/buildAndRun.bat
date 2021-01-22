@echo off
call mvn clean package
call docker build -t org.example/dmit2015-instructor-jpa-jsf-crud-demo .
call docker rm -f dmit2015-instructor-jpa-jsf-crud-demo
call docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-instructor-jpa-jsf-crud-demo org.example/dmit2015-instructor-jpa-jsf-crud-demo