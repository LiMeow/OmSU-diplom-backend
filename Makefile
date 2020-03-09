DOCKER_CONTAINER_NAME = diploma_db

DB_PASSWORD = password
DB_USER = admin
DB_NAME = dplm_db

all: build install_db_and_nginx_docker start_server

start-without-tests: build-without-tests start_server

build:
	mvn clean install

build-without-tests:
	mvn clean install \
	-Dmaven.test.skip=true \
	-Dbuild.format=jar \

start_server:
	cd ./target;java -jar ./schedule-1.0-SNAPSHOT.jar;cd ..

install_docker:
	sudo apt-get update
	sudo apt-get install docker docker-compose

install_db_and_nginx_docker:
	cd ./dev-env;sudo docker-compose up -d --build;cd ..

start_db_and_nginx_docker:
	sudo docker start diploma_db;sudo docker start dimploma_nginx

stop_db_and_nginx_docker:
	sudo docker stop diploma_db; sudo docker stop dimploma_nginx

remove_db_and_nginx_docker:
	cd ./dev-env;sudo docker-compose down --rmi all;cd ..

restart_db_and_nginx_docker: shutdown_db_and_nginx_docker start_db_and_nginx_docker

connect_to_psql_docker:
	sudo docker exec -it ${DOCKER_CONTAINER_NAME} psql ${DB_NAME} -U ${DB_USER}

