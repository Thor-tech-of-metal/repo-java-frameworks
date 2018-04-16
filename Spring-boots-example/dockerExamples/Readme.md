
Package the application
=======================

[skynet@localhost dockerExamples]$ ./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar


Setup docker hipser
===================
1 Your PATH should include /usr/local/bin

2 add your user to docker.
[skynet@localhost dockerExamples] sudo usermod -a -G libvirt,docker <username>

3 
[skynet@localhost dockerExamples] id <username>

4
[skynet@localhost dockerExamples] systemctl start docker
[skynet@localhost dockerExamples] systemctl enable docker

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


https://docs.openshift.org/latest/minishift/openshift/openshift-docker-registry.html

https://docs.openshift.org/latest/minishift/using/docker-daemon.html

https://www.youtube.com/watch?v=r5VzXvvkiL4
