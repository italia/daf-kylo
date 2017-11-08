FROM mariadb:10.3

COPY scripts/setup-mysql.sh /docker-entrypoint-initdb.d

RUN mkdir -p /usr/local/bin/kylo
COPY dist/. /usr/local/bin/kylo
