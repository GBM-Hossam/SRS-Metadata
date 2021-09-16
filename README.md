# SRS-Metadata
Metadata School Registration System

 Build APP:
 -mvn --version
 -mvn clean package spring-boot:repackage
 
 Run APP:
 Step 1: create network
 CMD: docker network create srs-mysql
 
 Step 2: Pull Mysql latest image
 CMD:  docker container run --name mysqldb --network srs-mysql -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=srs_db -d mysql:8
 
 Step 3: Build App image from the Dockerfile 
 CMD:  docker build -t metadata/srs .

 Hint: Don't start App image until you make sure MYSQL up and running (it takes few minutes for DB to be up), you can check that as following
 - docker container exec -it mysqldb bash
 - mysql -uroot -p  (enter pwd: admin)
 - Show databases;
 - Show tables;
 
 Step 4: Run APP Image
 CMD: docker container run --network srs-mysql --name app -p 8080:8080 -d metadata/srs


