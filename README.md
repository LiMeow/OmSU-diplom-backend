# OmSU-Diplom-Backend

## Setup local database

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

## How to build

Run following command from project root directory:

```shell script
mvn clean install
```

## How to run Swagger

Follow the link below in your browser:

```shell script
http://localhost:8080/api/swagger-ui.html
```
