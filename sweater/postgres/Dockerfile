FROM postgres:14 AS postgres_with_script
COPY 10-create-user-and-db.sql /docker-entrypoint-initdb.d/10-create-user-and-db.sql
