#!/bin/bash
mysqlservicepod=$(kubectl get pods -o yaml | sed -En 's/name: tba-mysql-(.*)-(.*)/tba-mysql-\1-\2/p')
echo "Mysql service pod is $mysqlservicepod"
kubectl exec -it $mysqlservicepod -- /bin/bash
