#!/bin/bash -x

namespace="default"

if [ "$1" = 'test' ]
then
    ENV='.'$1
    elif [ "$1" = 'prod' ]
    then
    ENV=''
fi

echo "Working on $1 environment"

case $2 in

  all)
	kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV --recursive
	kubectl delete --namespace="$namespace" -f kubernetes/deployment$ENV --recursive
	kubectl delete --namespace="$namespace" -f kubernetes/service --recursive
	;;
  activemq)
	kubectl delete --namespace="$namespace" -f kubernetes/deployment$ENV/activemq.yaml
	kubectl delete --namespace="$namespace" -f kubernetes/service/activemq.yaml
	;;
  mysql)
	kubectl delete --namespace="$namespace" -f kubernetes/deployment$ENV/mysql.yaml
	kubectl delete --namespace="$namespace" -f kubernetes/service/mysql.yaml
	;;
  kylo-services)
    #kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV/kylo-services-spark.yaml
	kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV/kylo-services.yaml
	kubectl delete --namespace="$namespace" -f kubernetes/deployment$ENV/kylo-services.yaml
	kubectl delete --namespace="$namespace" -f kubernetes/service/kylo-services.yaml
    ;;
  kylo-ui)
	kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV/kylo-ui.yaml
	kubectl delete --namespace="$namespace" -f kubernetes/deployment$ENV/kylo-ui.yaml
	kubectl delete --namespace="$namespace" -f kubernetes/service/kylo-ui.yaml
	;;
  nifi)
    if [ "$1" = 'prod' ]
    then
        kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV/nifi.yaml
        kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV/nifi-kylo.yaml
        kubectl delete --namespace="$namespace" -f kubernetes/deployment$ENV/nifi.yaml
        kubectl delete --namespace="$namespace" -f kubernetes/service/nifi.yaml
    else
        echo "This feature is deprecated for testing environment; use nifi-cluster"
    fi
    ;;
  nifi-cluster)
    if [ "$1" = 'test' ]
    then
        kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV/nifi-cluster.yaml
        kubectl delete --namespace="$namespace" -f kubernetes/config-map$ENV/nifi-kylo.yaml
        kubectl delete --namespace="$namespace" -f kubernetes/deployment$ENV/nifi-cluster.yaml
        kubectl delete --namespace="$namespace" -f kubernetes/service/nifi-cluster.yaml
    else
        echo "This feature is not available for production environment"
    fi
    ;;
esac
