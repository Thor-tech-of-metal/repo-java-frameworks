
Package the application
=======================

[skynet@localhost dockerExamples]$ ./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar



Start docker hipser
===================
sudo systemctl start docker


Build a docker image
====================
[skynet@localhost dockerExamples]$ sudo ./mvnw install dockerfile:build

Push docker image
=================
[skynet@localhost dockerExamples]$ sudo ./mvnw dockerfile:push


Build and deploy manually
=========================

*) [skynet@localhost dockerExamples]$ sudo  docker build -t "dockerexamples" .

*) [skynet@localhost dockerExamples]$ sudo  docker image ls

Notes : run and map internal port from 8080 to 8080

*) [skynet@localhost dockerExamples]$ sudo  docker run -p 8080:8080 -t dockerexamples


Enpoint
=======
http://localhost:8080/greeting


