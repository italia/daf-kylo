#!/bin/bash

case $1 in

kylo-services)
    kyloservicespod=$(kubectl get pods -o yaml | sed -En 's/name: tba-kylo-services-(.*)-(.*)/tba-kylo-services-\1-\2/p')
    echo "kylo ui pod is $kyloservicespod"
    kubectl port-forward $kyloservicespod 8420:8420
  ;;
kylo-ui)
    kylouipod=$(kubectl get pods -o yaml | sed -En 's/name: tba-kylo-ui-(.*)-(.*)/tba-kylo-ui-\1-\2/p')
    echo "kylo ui pod is $kylouipod"
    kubectl port-forward $kylouipod 8400:8400
  ;;
nifi)
    nifipod=$(kubectl get pods -o yaml | sed -En 's/name: tba-nifi-(.*)-(.*)/tba-nifi-\1-\2/p')
    echo "nifi service pod is $nifipod"
    kubectl port-forward $nifipod 8080:8080
    ;;
activemq)
    activemqpod=$(kubectl get pods -o yaml | sed -En 's/name: tba-activemq-(.*)-(.*)/tba-activemq-\1-\2/p')
    echo "activemq service pod is $activemqpod"
    kubectl port-forward $activemqpod 8161:8161
    ;;
mysql)
    mysqlpod=$(kubectl get pods -o yaml | sed -En 's/name: tba-mysql-(.*)-(.*)/tba-mysql-\1-\2/p')
    echo "mysql service pod is $mysqlpod"
    kubectl port-forward $mysqlpod 3306:3306
    ;;
nifi-cluster)
    kubectl port-forward svc/tba-nifi 8080:8080
    ;;
*)
    echo "Sorry, I can not get a pod for you!"
    echo "Cases are nifi, nifi-cluster, kylo-services, kylo-ui, mysql, activemq";;
esac

