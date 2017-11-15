#!/bin/bash

echo "Executing the master script to install all database scripts"

/usr/local/bin/kylo/0.2.0/setup-mysql.sh localhost root hadoop
/usr/local/bin/kylo/0.3.0/update.sh localhost root hadoop
/usr/local/bin/kylo/0.3.1/update.sh localhost root hadoop
/usr/local/bin/kylo/0.4.0/update.sh localhost root hadoop
/usr/local/bin/kylo/0.4.2/update.sh localhost root hadoop
/usr/local/bin/kylo/0.5.0/update.sh localhost root hadoop
/usr/local/bin/kylo/0.6.0/update.sh localhost root hadoop
/usr/local/bin/kylo/0.7.0/update.sh localhost root hadoop
/usr/local/bin/kylo/0.7.1/update.sh localhost root hadoop
/usr/local/bin/kylo/0.8.0/update.sh localhost root hadoop

mysql -h localhost -u root --password=hadoop -e 'show databases;'

echo "Database installation complete"
