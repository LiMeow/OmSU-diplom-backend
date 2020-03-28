# OmSU-Diplom-Backend

## How to start

First you need install docker and docker compose.

```shell script
make install_docker
```

Then just run make.

```shell script
make
```

## How to build

For building with tests:

```shell script
make build
```

For building without tests

```shell script
make build-without-tests
```

## How to start server

For starting server 

```shell script
make start_server
```

## How to stop/start docker containers

Run one of the following commands:

```shell script
make start_db_and_nginx_docker
```
```shell script
make stop_db_and_nginx_docker
```

## How to connect to db in docker

For connecting to postgres that runnin in docker:

```shell script
make connect_to_psql_docker
```

## How to run Swagger

Follow the link below in your browser:

```shell script
http://localhost:8080/api/swagger-ui.html
```

## Setup local database

If you need use it local without docker.
PostgreSQL 9 is assumed.

```shell script
sudo apt-get install postgresql
sudo su postgres
psql
```

```sql
CREATE USER admin WITH PASSWORD 'password';
CREATE DATABASE dplm_db OWNER admin;
```

Then type `\q` to quit from `psql` command. 

## Liquibase migration

To update local database to current state run following command.

```shell script
mvn liquibase:update \
  -Dliquibase.url=jdbc:postgresql://localhost:5432/dplm_db \
  -Dliquibase.username=admin \
  -Dliquibase.password=password
```

Command to rollback one migration:

```shell script
mvn liquibase:rollback \
  -Dliquibase.url=jdbc:postgresql://localhost:5432/dplm_db \
  -Dliquibase.username=admin \
  -Dliquibase.password=password \
  -Dliquibase.rollbackCount=1
```
